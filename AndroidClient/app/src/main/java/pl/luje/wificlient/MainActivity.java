package pl.luje.wificlient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView textResponse;
    private EditText editTextAddress, editTextPort;
    private Button startButton, pauseButton, stopButton, forwardButton, reverseButton, previousButton, nextButton;
    private ListView audioFilesListView;
    private ArrayAdapter<String> titlesAdapter;
    private String cars[] = {"Mercedes", "Fiat", "Ferrari", "Aston Martin", "Lamborghini", "Skoda", "Volkswagen", "Audi", "Citroen"};
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        editTextAddress = (EditText) findViewById(R.id.address);
        editTextPort = (EditText) findViewById(R.id.port);
        textResponse = (TextView) findViewById(R.id.response);
        startButton = (Button) findViewById(R.id.startButton);
        pauseButton = (Button) findViewById(R.id.pauseButton);
        stopButton = (Button) findViewById(R.id.stopButton);
        forwardButton = (Button) findViewById(R.id.forwardButton);
        reverseButton = (Button) findViewById(R.id.reverseButton);
        previousButton = (Button) findViewById(R.id.previousButton);
        nextButton = (Button) findViewById(R.id.nextButton);
        audioFilesListView = (ListView) findViewById(R.id.audioFilesListView);

        startButton.setOnClickListener(startButtonOnClickListener);
        pauseButton.setOnClickListener(pauseButtonOnClickListener);
        stopButton.setOnClickListener(stopButtonOnClickListener);
        forwardButton.setOnClickListener(forwardButtonOnClickListener);
        reverseButton.setOnClickListener(reverseButtonOnClickListener);
        previousButton.setOnClickListener(previousButtonOnClickListener);
        nextButton.setOnClickListener(nextButtonOnClickListener);

        initializeAudioFilesListView();

        /*audioFilesListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context, "Selected item number " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(context, "Nothing selected", Toast.LENGTH_SHORT).show();
            }

        });*/

        audioFilesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context, "Clicked item number " + position, Toast.LENGTH_SHORT).show();
                audioFilesListView.setSelection(position);
            }
        });
    }

    private void initializeAudioFilesListView() {
        ArrayList<String> carL = new ArrayList<String>();
        carL.addAll(Arrays.asList(cars));

        //ToDo: Ogarnąć jak powinien działać poprawnie customowy selector.
        //audioFilesListView.setSelector(R.drawable.audio_files_list_view_selector);
        audioFilesListView.setAdapter(new AudioFilesListViewAdapter(context, carL));
    }

    OnClickListener startButtonOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View arg0) {
            sendMessage("START");
        }
    };

    OnClickListener pauseButtonOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View arg0) {
            sendMessage("PAUSE");
        }
    };

    OnClickListener stopButtonOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View arg0) {
            sendMessage("STOP");
        }
    };

    OnClickListener forwardButtonOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View arg0) {
            sendMessage("FORWARD");
        }
    };

    OnClickListener reverseButtonOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View arg0) {
            sendMessage("REVERSE");
        }
    };

    OnClickListener previousButtonOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View arg0) {
            sendMessage("PREVIOUS");
        }
    };

    OnClickListener nextButtonOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View arg0) {
            sendMessage("NEXT");
        }
    };

    private void sendMessage(String param) {
        MyClientTask myClientTask = new MyClientTask(
                editTextAddress.getText().toString(),
                Integer.parseInt(editTextPort.getText().toString()),
                param);
        myClientTask.execute();
    }

    public class MyClientTask extends AsyncTask<Void, Void, Void> {

        String dstAddress;
        int dstPort;
        String response = "";
        String msgToServer;

        MyClientTask(String addr, int port, String msgTo) {
            dstAddress = addr;
            dstPort = port;
            msgToServer = msgTo;
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            Socket socket = null;
            DataOutputStream dataOutputStream = null;
            DataInputStream dataInputStream = null;

            try {
                socket = new Socket(dstAddress, dstPort);
                dataOutputStream = new DataOutputStream(
                        socket.getOutputStream());
                dataInputStream = new DataInputStream(socket.getInputStream());

                if (msgToServer != null) {
                    dataOutputStream.writeUTF(msgToServer + "<EOF>");
                    //dataOutputStream.writeUTF(msgToServer);
                }

                response = dataInputStream.readUTF();

            } catch (UnknownHostException e) {
                e.printStackTrace();
                response = "UnknownHostException: " + e.toString();
            } catch (IOException e) {
                e.printStackTrace();
                response = "IOException: " + e.toString();
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (dataOutputStream != null) {
                    try {
                        dataOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (dataInputStream != null) {
                    try {
                        dataInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            textResponse.setText(response);
            super.onPostExecute(result);
        }

    }

}