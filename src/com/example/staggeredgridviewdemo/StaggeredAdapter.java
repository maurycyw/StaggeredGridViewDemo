package com.example.staggeredgridviewdemo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.staggeredgridviewdemo.loader.ImageLoader;
import com.example.staggeredgridviewdemo.views.ScaleImageView;

public class StaggeredAdapter extends ArrayAdapter<String> {

	private ImageLoader mLoader;
	
	//记录图片的多选
	private Map<String,String> map = new HashMap<String, String>();
	

	public StaggeredAdapter(Context context, int textViewResourceId,
			String[] objects) {
		super(context, textViewResourceId, objects);
		mLoader = new ImageLoader(context);
		
		//初始化map 默认都是没有选中状态
		for (int i = 0; i < objects.length; i++) {
			String temp =String.valueOf(i);
			map.put(temp, "false");
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;

		if (convertView == null) {
			LayoutInflater layoutInflator = LayoutInflater.from(getContext());
			convertView = layoutInflator.inflate(R.layout.row_staggered_demo,
					null);
			holder = new ViewHolder();
			holder.imageView = (ScaleImageView) convertView .findViewById(R.id.imageView1);
			convertView.setTag(holder);
		}

		holder = (ViewHolder) convertView.getTag();

		mLoader.DisplayImage(getItem(position), holder.imageView);
		
		
		// 增加图片多选状态
		 Resources res=convertView.getResources();    
	    
	    Bitmap bmp=BitmapFactory.decodeResource(res,R.drawable.select01); 
		
		if(map.get(selectid).equals("true"))
		{	
			holder.imageView.setImageBitmap(this.compositeImages(this.drawableToBitmap(holder.imageView.getDrawable()), bmp) );
		}	
		
		
		// 图片点击更新是否选中
		holder.imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.i("adapter", "---------------dianwole="+arg0);
				
				if(map.get(selectid).equals("true"))
				{
					map.put(selectid, "false");
				}
				else
				{
					map.put(selectid, "true");
				}
				notifyDataSetChanged();
			}
		});
		
		//
		
		
		
		

		return convertView;
	}

	static class ViewHolder {
		ScaleImageView imageView;
	}
	
	
	
	// 图片合成方法
	  private Bitmap compositeImages(Bitmap srcBitmap,Bitmap dstBitmap){   
          
	        Bitmap bmp = null;   
	        //下面这个Bitmap中创建的函数就可以创建一个空的Bitmap   
	        bmp = Bitmap.createBitmap(srcBitmap.getWidth(), srcBitmap.getHeight(), srcBitmap.getConfig());   
	        Paint paint = new Paint();   
	        Canvas canvas = new Canvas(bmp);   
	        //首先绘制第一张图片，很简单，就是和方法中getDstImage一样   
	        canvas.drawBitmap(srcBitmap, 0, 0, paint);         
	           
	        //在绘制第二张图片的时候，我们需要指定一个Xfermode   
	        //这里采用Multiply模式，这个模式是将两张图片的对应的点的像素相乘   
	        //，再除以255，然后以新的像素来重新绘制显示合成后的图像   
	        
	        Log.i("srcBitmap.getWidth()","srcBitmap.getWidth()="+srcBitmap.getWidth());
	        
	        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));   
	        canvas.drawBitmap(dstBitmap, srcBitmap.getWidth()-dstBitmap.getWidth()-10, 0, paint);   
	           
	        return bmp;   
	    }   
	
	
	  private Bitmap drawableToBitmap(Drawable drawable) {    
          
	        Bitmap bitmap = Bitmap    
	                        .createBitmap(    
	                                        drawable.getIntrinsicWidth(),    
	                                        drawable.getIntrinsicHeight(),    
	                                        drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888    
	                                                        : Bitmap.Config.RGB_565);    
	        Canvas canvas =  new  Canvas(bitmap);    
	         //canvas.setBitmap(bitmap);    
	        drawable.setBounds( 0 ,  0 , drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());    
	        drawable.draw(canvas);    
	        return  bitmap;    
	}   
	
	
}
