package io.evercam.androidapp.sharing;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import io.evercam.CameraShareInterface;
import io.evercam.PatchCameraBuilder;
import io.evercam.androidapp.ParentAppCompatActivity;
import io.evercam.androidapp.R;
import io.evercam.androidapp.custom.CustomedDialog;
import io.evercam.androidapp.dto.EvercamCamera;
import io.evercam.androidapp.video.VideoActivity;

public class SharingActivity extends ParentAppCompatActivity
{
    private static final String TAG = "SharingActivity";

    private static EvercamCamera evercamCamera;

    public SharingListFragment sharingListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        evercamCamera = VideoActivity.evercamCamera;

        setContentView(R.layout.activity_sharing);

        setUpDefaultToolbar();

        sharingListFragment = new SharingListFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, sharingListFragment)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static class SharingListFragment extends ListFragment
    {
        private ImageView mSharingStatusImageView;
        private TextView mSharingStatusTextView;
        private TextView mSharingStatusDetailTextView;

        String[] testArray = new String[] { "one", "two", "three", "four",
                "five", "six", "seven", "eight", "nine", "ten", "eleven",
                "twelve", "thirteen", "fourteen", "fifteen" };

        @Override
        public void onListItemClick(ListView listView, View view, int position, long id)
        {
            //If list header is clicked
            if(position == 0)
            {
                CustomedDialog.getShareStatusDialog(this).show();
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState)
        {
            return super.onCreateView(inflater, container, savedInstanceState);
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState)
        {
            super.onActivityCreated(savedInstanceState);

            View headerView = getActivity().getLayoutInflater().inflate(R.layout.share_list_header,getListView(),false);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getActivity().getLayoutInflater().getContext(), android.R.layout.simple_list_item_1,
                    testArray);

            setListAdapter(null);
            //Add header for the sharing status
            getListView().addHeaderView(headerView);
            //Remove divider from list
            getListView().setDivider(null);
            setListAdapter(adapter);

            mSharingStatusImageView = (ImageView) headerView.findViewById(R.id.share_status_icon_image_view);
            mSharingStatusTextView = (TextView) headerView.findViewById(R.id.sharing_status_text_view);
            mSharingStatusDetailTextView = (TextView) headerView.findViewById(R.id.sharing_status_detail_text_view);

            retrieveSharingStatusFromCamera();
        }

        public void retrieveSharingStatusFromCamera()
        {
            SharingStatus status = new SharingStatus(evercamCamera.isDiscoverable(),
                    evercamCamera.isPublic());

            updateSharingStatusUi(status);
        }

        public void updateSharingStatusUi(SharingStatus status)
        {
            mSharingStatusImageView.setImageResource(status.getImageResourceId());
            mSharingStatusTextView.setText(status.getStatusStringId());
            mSharingStatusDetailTextView.setText(status.getStatusDetailStringId());
        }

        public void patchSharingStatusAndUpdateUi(SharingStatus status)
        {
            buildPatchCamera(status).build()
        }

        private PatchCameraBuilder buildPatchCamera(SharingStatus status)
        {
            PatchCameraBuilder patchCameraBuilder = new PatchCameraBuilder(evercamCamera.getCameraId());
            //patchCameraBuilder.setPublic(status.isPublic()).set
            return patchCameraBuilder;
        }
    }

    public class ShareListArrayAdapter extends ArrayAdapter<CameraShareInterface>
    {

        public ShareListArrayAdapter(Context context, int resource)
        {
            super(context, resource);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            return super.getView(position, convertView, parent);
        }
    }
}
