package fsktm.um.edu.my.mysedekah;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import fsktm.um.edu.my.mysedekah.campaignlist.campaignListContent;
import fsktm.um.edu.my.mysedekah.campaignlist.campaignListItems;
import fsktm.um.edu.my.mysedekah.campaigndb.campaignhelper;
import fsktm.um.edu.my.mysedekah.campaigndb.campaigncontent;


public class CampaignActivity extends AppCompatActivity implements CampaignViewFragment.OnListFragmentInteractionListener{


    public String user_id = "40";
    private CampaignActivity context;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listcampaign);
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;

        if (recyclerViewAdapter == null) {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.main_fragment);
            recyclerView = (RecyclerView) currentFragment.getView();
            recyclerViewAdapter = ((RecyclerView) currentFragment.getView()).getAdapter();
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                    DividerItemDecoration.VERTICAL);
            recyclerView.addItemDecoration(dividerItemDecoration);
        };

        campaignhelper helper = new campaignhelper(this);
        List<campaigncontent> cc = helper.getAllCampaigns();
        campaignListContent.ITEMS.clear();
        for(int x = 0 ; x < cc.size() ; x+=1){
            campaignListItems f = new campaignListItems();
            f.img = blobToDrawable(cc.get(x).getImg());
            f.title = cc.get(x).getTitle();
            f.desc = cc.get(x).getDesc();
            f._id = cc.get(x).get_id();
            campaignListContent.loadCampaign(f);
        }
        recyclerViewAdapter.notifyItemInserted(0);
    }
    public Drawable blobToDrawable(byte[] image){
        Drawable img = new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(image, 0, image.length));
        return img;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onListFragmentInteraction(campaignListItems item) {
        Intent viewCampaign= new Intent(this, ViewActivity.class);
        viewCampaign.putExtra("id", item._id);
        viewCampaign.putExtra("user_id", user_id);
        startActivity(viewCampaign);
    }
}
