package edu.upc.eseiaat.pma.bruna.shoppinglist;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static edu.upc.eseiaat.pma.bruna.shoppinglist.R.id.edit_item;
import static edu.upc.eseiaat.pma.bruna.shoppinglist.R.id.edit_item;

public class ShoppingListActivity extends AppCompatActivity {


    private ArrayList<String> itemlist;
    private ArrayAdapter<String> adapter;
    private ListView list;
    private Button button;
    private EditText edittext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        list = (ListView) findViewById(R.id.list);
        button = (Button) findViewById(R.id.button);
        edittext = (EditText) findViewById(edit_item);

        itemlist = new ArrayList<>();
        itemlist.add("Patates");
        itemlist.add("Pastanagues");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemlist);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem();
            }
        });
        edittext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                addItem();
                return true;
            }
        });

        list.setAdapter(adapter);

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> list, View item, int pos, long id) {
                maybeRemoveItem(pos);
                return true;
            }
        });
    }

    private void maybeRemoveItem(final int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.confirm);
        String fmt = getResources().getString(R.string.confirm_message);
        builder.setMessage(String.format(fmt,itemlist.get(pos)));
        builder.setPositiveButton(R.string.Remove, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                itemlist.remove(pos);
                adapter.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton(android.R.string.cancel, null);
        builder.create().show();
    }

    private void addItem() {
        String item_text = edittext.getText().toString();
        if (!item_text.isEmpty()) {
            itemlist.add(item_text);
            adapter.notifyDataSetChanged();
            edittext.setText("");
        }
    }
}