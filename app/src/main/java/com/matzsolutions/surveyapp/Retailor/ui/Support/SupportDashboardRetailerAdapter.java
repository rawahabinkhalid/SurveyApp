package com.matzsolutions.surveyapp.Retailor.ui.Support;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.matzsolutions.surveyapp.CustomToast;
import com.matzsolutions.surveyapp.R;
import com.matzsolutions.surveyapp.Retailer_Login.RetailerLogin;

import org.json.JSONException;

import java.util.List;

public class SupportDashboardRetailerAdapter extends RecyclerView.Adapter<SupportDashboardRetailerAdapter.ViewHolder> {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    Context mContxt;
    Context activity;
    String dashboard, id, pending, createdDate;
    List<SurveyModel> supportList;
//    private String URL_SUPPORT_VIEW = "https://175.107.203.97:4014/api/support/TicketById/";

    public SupportDashboardRetailerAdapter(Activity activity, Context applicationContext, String dashboard, String id, String pending, String createdDate) {
//        this.mContxt = applicationContext;
//        this.activity = activity;
//        this.dashboard = dashboard;
//        this.id = id;
//        this.pending = pending;
//        this.createdDate = createdDate;
    }

    public SupportDashboardRetailerAdapter(Context context, List<SurveyModel> supportList) {
        this.mContxt = context;
        this.supportList = supportList;
        this.recyclerView = recyclerView;
    }

    @Override
    public SupportDashboardRetailerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(mContxt).inflate(R.layout.layout_support_rv, parent, false);
        return new SupportDashboardRetailerAdapter.ViewHolder(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull final SupportDashboardRetailerAdapter.ViewHolder holder, final int position) {
        // Log.i("DebugSupportFilter", supportList.get(position).getIssueType());
        // Log.i("DebugSupportFilter_1", String.valueOf(position));
        // Log.i("DebugSupportFilter_2", String.valueOf((supportList.size() - 1)));
//         if(supportList.size() == 3 || supportList.size() == 4) {
//             if (position == (supportList.size() - 1)) {
// //        if (position == 2) {
//                 // Log.i("DebugSupportFilter_In", supportList.get(position).getIssueType());
//                 RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
//                         RelativeLayout.LayoutParams.WRAP_CONTENT,
//                         RelativeLayout.LayoutParams.WRAP_CONTENT
//                 );
//                 params.setMargins(0, 50, 0, 360);
//                 holder.main_layout_support_box_retailer.setLayoutParams(params);
//             }
//         }
        holder.heading.setText(supportList.get(position).getTitle());
        holder.organization_name_value.setText(supportList.get(position).getOrgName());
//        holder.ticket_id_value.setText(supportList.get(position).getTicketNumber() + "\n\n\n\n1");
        holder.deadline_value.setText(supportList.get(position).getDeadline());


        holder.menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences SupportId = ((FragmentActivity) mContxt).getSharedPreferences("SupportId",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = SupportId.edit();
                editor.putString("SupportId", String.valueOf(supportList.get(position).getSurveyID()));
                editor.apply();

                Context wrapper = new ContextThemeWrapper(mContxt, R.style.AppBaseTheme);
                final PopupMenu popup = new androidx.appcompat.widget.PopupMenu(wrapper, view);
                MenuInflater inflater = popup.getMenuInflater();
                if (!supportList.get(position).getAnswerID().equals("NoAnswers")) {
                    inflater.inflate(R.menu.menu_items, popup.getMenu());
                } else {
                    inflater.inflate(R.menu.menu_items_fill, popup.getMenu());
                }
                FragmentTransaction fragmentTransaction;
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_view:

                                FragmentTransaction fragmentTransaction = ((FragmentActivity) mContxt).getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.add(R.id.main_container_ret, new Retailer_Support_Ticket_View());
                                fragmentTransaction.commit();
                                break;
                            case R.id.menu_fill:
                                if (supportList.get(position).getIsActive() == 1) {
                                    FragmentTransaction fragmentTransactionFill = ((FragmentActivity) mContxt).getSupportFragmentManager().beginTransaction();
                                    fragmentTransactionFill.add(R.id.main_container_ret, new Support_Ticket_Form_Fragment());
                                    fragmentTransactionFill.commit();
                                } else {
                                    new CustomToast().showToast((FragmentActivity) mContxt, "This survey is no more active.");
                                }
                                break;

                        }
                        return false;
                    }
                });
                popup.show();
            }
        });
    }


    private void supportView(final int position) {

    }

    private void DeleteSupportTicket(String ID) throws JSONException {
        DeleteSupportTicket deleteSupport = new DeleteSupportTicket();
        String response = deleteSupport.DeleteSupportTicket(mContxt, ID);

    }

    @Override
    public int getItemCount() {
        return supportList.size();
//        return 3;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView heading, organization_name_value, deadline_value;
        public RelativeLayout main_layout_support_box_retailer;
        public ImageButton menu_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            main_layout_support_box_retailer = itemView.findViewById(R.id.main_layout_support_box_retailer);
            heading = itemView.findViewById(R.id.heading);
            organization_name_value = itemView.findViewById(R.id.organization_name_value);
            deadline_value = itemView.findViewById(R.id.deadline_value);
            menu_btn = itemView.findViewById(R.id.menu_btn);
        }
    }


    // private void printErrorMessage(VolleyError error) {
    //     if (mContxt != null) {
    //         if (error instanceof NetworkError) {
    //             Toast.makeText(mContxt, "Network Error !", Toast.LENGTH_LONG).show();
    //         } else if (error instanceof ServerError) {
    //             Toast.makeText(mContxt, "Server Error !", Toast.LENGTH_LONG).show();
    //         } else if (error instanceof AuthFailureError) {
    //             Toast.makeText(mContxt, "Auth Failure Error !", Toast.LENGTH_LONG).show();
    //         } else if (error instanceof ParseError) {
    //             Toast.makeText(mContxt, "Parse Error !", Toast.LENGTH_LONG).show();
    //         } else if (error instanceof NoConnectionError) {
    //             Toast.makeText(mContxt, "No Connection Error !", Toast.LENGTH_LONG).show();
    //         } else if (error instanceof TimeoutError) {
    //             Toast.makeText(mContxt, "Timeout Error !", Toast.LENGTH_LONG).show();
    //         }

    //         if (error.networkResponse != null && error.networkResponse.data != null) {
    //             try {
    //                 String message = "";
    //                 String responseBody = new String(error.networkResponse.data, "utf-8");
    //                 // Log.i("responseBody", responseBody);
    //                 JSONObject data = new JSONObject(responseBody);
    //                 // Log.i("data", String.valueOf(data));
    //                 Iterator<String> keys = data.keys();
    //                 while (keys.hasNext()) {
    //                     String key = keys.next();
    //                     message = message + data.get(key) + "\n";
    //                 }
    //                 Toast.makeText(mContxt, message, Toast.LENGTH_LONG).show();
    //             } catch (UnsupportedEncodingException e) {
    //                 e.printStackTrace();
    //             } catch (JSONException e) {
    //                 e.printStackTrace();
    //             }
    //         }
    //     }

    // }
}
