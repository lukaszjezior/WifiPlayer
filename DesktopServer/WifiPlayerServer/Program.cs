using System;
using System.Collections.Generic;
using System.Linq;
using System.Windows.Forms;
using TinyMessenger;

namespace WifiPlayerServer
{
    static class Program
    {
        public static TinyMessengerHub MESSAGE_HUB = new TinyMessengerHub();
        public static ConnectionService CONNECTION_SERVICE = new ConnectionService();

        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new MainForm());
        }

    }
}
