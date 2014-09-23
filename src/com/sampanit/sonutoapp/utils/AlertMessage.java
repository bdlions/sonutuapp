package com.sampanit.sonutoapp.utils;

import com.sportzweb.R;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class AlertMessage {

	public static void cancelProgress(final Context c,
			final ProgressDialog progressDialog) {

		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();

		}
	}

	public static void showMessage(final Context c, final String title,
			final String s) {
		final AlertDialog.Builder aBuilder = new AlertDialog.Builder(c);
		aBuilder.setTitle(title);
		// aBuilder.setIcon(R.drawable.icon);
		aBuilder.setMessage(s);

		aBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

			public void onClick(final DialogInterface dialog, final int which) {
				dialog.dismiss();
			}

		});

		aBuilder.show();
	}

	public static void showErrorMessage(final Context c, final String title,
			final String s) {
		final AlertDialog.Builder aBuilder = new AlertDialog.Builder(c);
		aBuilder.setTitle(title);
		aBuilder.setIcon(R.drawable.fail);
		aBuilder.setMessage(s);

		aBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

			public void onClick(final DialogInterface dialog, final int which) {
				dialog.dismiss();
			}

		});

		aBuilder.show();
	}

	public static void showCustomToast(Context con, String text, int drawable) {

		final LinearLayout layout = new LinearLayout(con);

		final LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);

		layout.setLayoutParams(params);
		layout.setOrientation(0);

		final ImageView image = new ImageView(con);
		image.setImageResource(drawable);

		final TextView textView = new TextView(con);

		textView.setText(text);
		textView.setTextColor(Color.BLACK);
		textView.setTextSize(20.0f);

		layout.addView(image);
		layout.addView(textView);

		final Toast toast = new Toast(con);
		toast.setGravity(Gravity.TOP, 10, 100);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(layout);
		toast.show();
	}

	public static void showProgress(final Context c,
			ProgressDialog progressDialog) {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(c);
		}
		if (progressDialog != null && !progressDialog.isShowing()) {
			progressDialog = ProgressDialog.show(c, "Please wait...",
					"Buffering...", true, true);
		}

	}
}
