package com.rodrigojgxm.inlocoopenweather.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rodrigojgxm.inlocoopenweather.R;
import com.rodrigojgxm.inlocoopenweather.model.CityWeatherWrapper;

import java.util.List;

/**
 * Classe que define como objetos CityWeatherWrapper serão exibidos na tela
 * Created by Rodrigojgxm.
 */

public class CityWeatherAdapter extends ArrayAdapter<CityWeatherWrapper> {

    private Context mContext;
    private List<CityWeatherWrapper> mItens;

    public CityWeatherAdapter(Context context, List<CityWeatherWrapper> itens){
        super(context, R.layout.list_result_item,itens);
        mContext = context;
        mItens = itens;
    }

    /**
     * Método responsável pela conversão do objeto CityWeatherWrapper em uma view.
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)
                    mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_result_item, parent, false);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.result_name);
        CityWeatherWrapper wrapper = mItens.get(position);
        tv.setText(wrapper.getName());

        return convertView;
    }
}
