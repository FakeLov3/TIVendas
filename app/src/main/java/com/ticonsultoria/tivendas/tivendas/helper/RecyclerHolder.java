package com.ticonsultoria.tivendas.tivendas.helper;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ticonsultoria.tivendas.tivendas.R;

/**
 * Created by mpire on 17/01/2018.
 */

public class RecyclerHolder extends RecyclerView.ViewHolder {

    public TextView title;
    public ImageButton editButton;
    public ImageButton deleteButton;

    public RecyclerHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.txt_line_layout_name);
        editButton = (ImageButton) itemView.findViewById(R.id.btn_line_layout_edit);
        deleteButton = (ImageButton) itemView.findViewById(R.id.btn_line_layout_delete);
    }
}
