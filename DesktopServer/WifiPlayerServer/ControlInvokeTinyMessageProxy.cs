using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using TinyMessenger;

namespace WifiPlayerServer
{
    public class ControlInvokeTinyMessageProxy : ITinyMessageProxy
    {
        private WeakReference _Control;

        /// <summary>
        /// Initializes a new instance of the ControlInvokeTinyMessageProxy class.
        /// </summary>
        /// <param name="control"></param>
        public ControlInvokeTinyMessageProxy(Control control)
        {
            _Control = new WeakReference(control);
        }

        public void Deliver(ITinyMessage message, ITinyMessageSubscription subscription)
        {
            var control = _Control.Target as Control;

            if (control != null)
                control.Invoke((Action)delegate { subscription.Deliver(message); });
        }
    }
}
