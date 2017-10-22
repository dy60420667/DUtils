package dy.utils.libgaode.utils;

import android.text.TextUtils;

import java.io.Serializable;

public class LbsInfo implements Serializable {

	private static final long serialVersionUID = 4626360065948508156L;
	private String latitude = "";
	private String longitude = "";
	private String cityCode = "4201";// 武汉市
	private String cityName = "武汉";// 武汉市
	private String Province = "湖北";
	private String address="";

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getCityCode() {
		if(TextUtils.isEmpty(cityCode))
		{
			return "4201";
		}
		
		if(cityName.equals("武汉")||cityName.equals("武漢"))
		{
			return "4201";
		}
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getProvince() {
		return Province;
	}

	public void setProvince(String province) {
		Province = province;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
