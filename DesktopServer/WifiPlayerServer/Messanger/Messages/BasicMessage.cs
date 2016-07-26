using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TinyMessenger;

namespace WifiPlayerServer
{
    class BasicMessage : ITinyMessage
    {
        public BasicMessage(String ContentParam)
        {
            Content = ContentParam;
        }

        public String Content { get; private set; }

        public object Sender
        {
            get { throw new NotImplementedException(); }
        }
    }
}
