using NAudio.Wave;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace WifiPlayerServer.Player
{
    class AudioPlayer
    {
        private AudioFileReader audioFileReader;
        private IWavePlayer waveOutDevice;
        private Boolean isAudioInterfaceDisposed;

        public AudioPlayer()
        {
            initializeAudioInterface();
        }

        private void initializeAudioInterface()
        {
            waveOutDevice = new WaveOut();
            audioFileReader = new AudioFileReader("G:\\06 - Save Our Love.flac");
            waveOutDevice.Init(audioFileReader);
            isAudioInterfaceDisposed = false;
        }

        private void disposeAudioInterface()
        {
            audioFileReader.Dispose();
            waveOutDevice.Dispose();
            isAudioInterfaceDisposed = true;
        }

        public void Play()
        {
            if (isAudioInterfaceDisposed)
                initializeAudioInterface();
            waveOutDevice.Play();
        }

        public void Pause()
        {
            if (!isAudioInterfaceDisposed)
                waveOutDevice.Pause();
        }

        public void Stop() 
        {
            if (!isAudioInterfaceDisposed)
            {
                waveOutDevice.Stop();
                disposeAudioInterface();
            }
        }
    }
}
