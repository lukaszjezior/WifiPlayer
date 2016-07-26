using NAudio.Wave;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;
using TinyMessenger;
using WifiPlayerServer.Player;

namespace WifiPlayerServer
{
    public partial class MainForm : Form
    {
        ConnectionService connectionService = new ConnectionService();
        AudioPlayer audioPlayer = new AudioPlayer();

        public MainForm()
        {
            InitializeComponent();
            runService();
            songProgressBar.Minimum = 0;
            songProgressBar.Maximum = audioPlayer.getFullSongTimeInSeconds();

            Program.MESSAGE_HUB.Subscribe<BasicMessage>((basicMessage) => {
                if (basicMessage.Content.Equals("START"))
                {
                    audioPlayer.Play();
                }
                else if (basicMessage.Content.Equals("PAUSE"))
                {
                    audioPlayer.Pause();
                }
                else if (basicMessage.Content.Equals("STOP"))
                {
                    audioPlayer.Stop();
                }
                else if (basicMessage.Content.Equals("FORWARD"))
                {
                    audioPlayer.Forward();
                }
                else if (basicMessage.Content.Equals("REVERSE"))
                {
                    audioPlayer.Reverse();
                }
                String test = basicMessage.Content;
                label1.Text = test;
            }, new ControlInvokeTinyMessageProxy(this));
        }

        void runService()
        {
            new Thread(() =>
            {
                connectionService.StartListening();
            }).Start();
        }

    }
}
