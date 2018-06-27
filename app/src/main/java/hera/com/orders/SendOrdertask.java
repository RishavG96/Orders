package hera.com.orders;

import android.os.AsyncTask;

public class SendOrdertask extends AsyncTask<Void, Void, Boolean>
{
    hera.com.orders.service.Orders service_orders;
    @Override
    protected Boolean doInBackground(Void... voids) {
        service_orders=new hera.com.orders.service.Orders();
        service_orders.sendToServer(MainActivity.orderID);
        return null;
    }
}
