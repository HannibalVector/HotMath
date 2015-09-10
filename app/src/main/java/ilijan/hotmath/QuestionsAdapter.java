package ilijan.hotmath;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ilijan on 10.09.15..
 */
public class QuestionsAdapter extends ArrayAdapter<Item> {
    Context context;

    public QuestionsAdapter(Context context, int resource, List<Item> objects) {
        super(context, resource, objects);
        this.context = context;
    }

    public QuestionsAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater vi = LayoutInflater.from(getContext());
            convertView = vi.inflate(R.layout.questions_list_row, null);
        }

        Item item = getItem(position);

        if (item != null) {
            ImageView picture = (ImageView) convertView.findViewById(R.id.owner_picture);
            TextView name = (TextView) convertView.findViewById(R.id.owner_name);
            TextView title = (TextView) convertView.findViewById(R.id.title);
            TextView tags = (TextView) convertView.findViewById(R.id.tags);


            Owner owner = item.getOwner();

            Picasso.with(context).load(owner.getProfileImage()).into(picture);

            name.setText(owner.getDisplayName());
            title.setText(item.getTitle());

            StringBuilder tagsBuilder = new StringBuilder();
            for (String tag : item.getTags()) {
                tagsBuilder.append(tag).append(", ");
            }

            tags.setText(tagsBuilder.substring(0, tagsBuilder.length() - 2));
        }

        return convertView;
    }

}