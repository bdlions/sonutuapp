package com.sportzweb;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.bdlions.load.image.ImageLoader;
import com.google.gson.Gson;
import com.sonuto.Config;
import com.sonuto.newstabsswipe.NewsCommonFragment;
import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.register.NewsApp;
import com.sonuto.utils.component.CustomAdapter;
import com.sportzweb.JSONObjectModel.News;
import com.sportzweb.JSONObjectModel.NewsTab;
import com.sportzweb.JSONObjectModel.SubNewsTab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class NewsAppActivity extends Fragment {

	private final ArrayList<SubNewsTab> categoryNewsList = new ArrayList<SubNewsTab>();

	private ViewGroup container;
	private JSONObject firstNews;
	TextView firstNewsHeading;
	ImageView firstNewsImage;
	public ImageLoader imageLoader;
	private LayoutInflater inflater;
	ArrayList<String> item;
	JSONObject jsonCategoryNews;
	JSONArray jsonNewsSubTabs;

	JSONArray jsonTabs;
	ListView listView;
	JSONObject news;
	JSONArray newsJsonList;
	private String newsList;
	LinearLayout parentLayout;

	private View rootView;
	private final ArrayList<SubNewsTab> subCategoryNewsList = new ArrayList<SubNewsTab>();
	String subNewsHeading;
	private final ArrayList<NewsTab> tabList = new ArrayList<NewsTab>();
	TextView tv;

	@Override
	public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		for (final NewsTab tab : this.tabList) {
			menu.add(Menu.NONE, tab.getId(), Menu.NONE, tab.getTitle());

		}
		return;
	}

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

		this.rootView = inflater.inflate(R.layout.activity_all_news, container, false);
		this.inflater = inflater;
		this.container = container;

		this.imageLoader = new ImageLoader(getActivity().getApplicationContext());

		getActivity();
		final NewsApp newsApp = new NewsApp();

		getFragmentManager();

		newsApp.getHomePageData(new ICallBack() {

			@Override
			public void callBackErrorHandler(Object object) {
				// TODO Auto-generated method stub

			}

			@Override
			public void callBackResultHandler(Object object) {
				setHasOptionsMenu(true);
				// get json list of news tabs
				final JSONObject jsonObject = (JSONObject) object;

				try {
					final JSONArray news_category_list = jsonObject.getJSONArray("news_category_list");

					final Gson gson = new Gson();
					final int tabCount = news_category_list.length();
					for (int i = 0; i < tabCount; i++) {
						final NewsTab tab = gson.fromJson(news_category_list.get(i).toString(), NewsTab.class);
						NewsAppActivity.this.tabList.add(tab);
					}
					NewsAppActivity.this.newsList = jsonObject.getJSONArray("news_list").toString();

					NewsAppActivity.this.listView = (ListView) NewsAppActivity.this.rootView.findViewById(R.id.listViewNews);
					NewsAppActivity.this.firstNewsHeading = (TextView) NewsAppActivity.this.rootView.findViewById(R.id.firstNewsHeadline);
					NewsAppActivity.this.firstNewsImage = (ImageView) NewsAppActivity.this.rootView.findViewById(R.id.firstNewsImage);
					try {
						NewsAppActivity.this.newsJsonList = new JSONArray(NewsAppActivity.this.newsList);
						processNews(NewsAppActivity.this.newsJsonList);
					}
					catch (final JSONException e) {
						// TODO Auto-generated catch block
						// e.printStackTrace();
					}

				}
				catch (final JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		return this.rootView;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		final Intent i = new Intent(getActivity(), NewsCommonFragment.class);

		// Bundle mBundle = new Bundle();
		i.putExtra("tabId", item.getItemId());
		i.putExtra("newsList", this.newsList);

		startActivity(i);

		return true;
	}

	private void processNews(JSONArray newsList) {

		// ArrayList<String> item = new ArrayList<String>();
		final ArrayList<News> item = new ArrayList<News>();

		try {
			// JSONArray jsonArray = new JSONArray(newsList);
			this.firstNews = (JSONObject) newsList.get(0);

			this.firstNewsHeading.setText(this.firstNews.get("headline").toString());
			final String imagePath = Config.SERVER_ROOT_URL + "resources/images/applications/news_app/news/";

			this.firstNewsImage.setImageResource(R.drawable.upload_img_icon);
			this.imageLoader.DisplayImage(imagePath + this.firstNews.get("picture").toString(), this.firstNewsImage);

			this.firstNewsImage.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					try {
						final Intent intent = new Intent(getActivity(), NewsDetailsActivity.class);
						intent.putExtra("news_id", Integer.parseInt(NewsAppActivity.this.firstNews.get("news_id").toString()));
						intent.putExtra("news_category_title", "Home");

						getActivity().startActivity(intent);
					}
					catch (final JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			});

			final int newsCount = newsList.length();
			for (int i = 1; i < newsCount; i++) {
				final News newsObj = new News();
				this.news = (JSONObject) newsList.get(i);

				final String picture = this.news.get("picture").toString();
				final String description = this.news.get("headline").toString();
				final Integer id = this.news.getInt("news_id");

				newsObj.setId(id);
				newsObj.setTitle(description);
				newsObj.setPicture(imagePath + picture);

				item.add(newsObj);
			}

			final CustomAdapter adapter = new CustomAdapter(getActivity(), item);
			this.listView.setAdapter(adapter);

		}
		catch (final JSONException ie) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
	}
}
