package utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by c49 on 21/03/16.
 */
public class MyCustomAdapter extends BaseAdapter {

    ArrayList<String> result;
    Context context;

    private static LayoutInflater inflater=null;
    public MyCustomAdapter(MainActivity mainActivity,ArrayList<String> prgmNameList) {
        // TODO Auto-generated constructor stub
        result=prgmNameList;
        appendCachedData();
        context=mainActivity;
        inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    protected void appendCachedData() {
        if (result.size()<75) {
          for (int i=0;i<25;i++) { result.add("item"+result.size()); }
        }
        this.notifyDataSetChanged();
    }
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class ViewHolder
    {
        TextView tv;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View vi = convertView;
        ViewHolder holder;
        if(result.size()-10==position)
            appendCachedData();
        if(convertView==null){

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.program_list, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.tv = (TextView) vi.findViewById(R.id.textView1);
           ;

            /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );
        }
        else
            holder=(ViewHolder)vi.getTag();

        if(result.size()<=0)
        {
            holder.tv.setText("No Data");

        }
        else
        {


            /************  Set Model values in Holder elements ***********/

            holder.tv.setText(result.get(position) );

        }
        return vi;
    }
}
