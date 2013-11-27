package com.jalinsuara.android;

import java.util.ArrayList;
import java.util.HashMap;

import com.jalinsuara.android.projects.model.District;
import com.jalinsuara.android.projects.model.Province;
import com.jalinsuara.android.projects.model.SubDistrict;
import com.jalinsuara.android.projects.model.SubProject;

/**
 * Cache for share news
 * 
 * @author tonoman3g
 * 
 * 
 */
public class JalinSuaraCache {

	/**
	 * key - provinces id, value - province
	 */
	private HashMap<Long, Province> mProvincesCacheMap;

	/**
	 * key - district id, value - list of subdistrict
	 */
	private HashMap<Long, ArrayList<SubDistrict>> mSubDistrictCacheMap;

	/**
	 * key - province id, value - list of district
	 */
	private HashMap<Long, ArrayList<District>> mDistrictCacheMap;

	/**
	 * key - subdistrict id, value - list of subproject
	 */
	private HashMap<Long, ArrayList<SubProject>> mSubProjectCacheMap;

	public JalinSuaraCache() {
		initCache();
	}

	public ArrayList<District> getCachedDistricts(long key) {
		return mDistrictCacheMap.get(key);
	}

	public ArrayList<Province> getCachedProvinces() {
		if (mProvincesCacheMap != null) {
			return new ArrayList<Province>(mProvincesCacheMap.values());
		}
		return null;
	}

	public ArrayList<SubDistrict> getCachedSubDistricts(long key) {
		return mSubDistrictCacheMap.get(key);
	}

	public ArrayList<SubProject> getCachedSubProjects(long key) {
		return mSubProjectCacheMap.get(key);
	}

	public Province getProvince(long key) {
		return mProvincesCacheMap.get(key);
	}

	/**
	 * init cache
	 */
	private void initCache() {
		mProvincesCacheMap = new HashMap<Long, Province>();
		mDistrictCacheMap = new HashMap<Long, ArrayList<District>>();
		mSubDistrictCacheMap = new HashMap<Long, ArrayList<SubDistrict>>();
		mSubProjectCacheMap = new HashMap<Long, ArrayList<SubProject>>();
	}

	/**
	 * Is district cached
	 * 
	 * @param provinceId
	 * @return
	 */
	public boolean isDistrictsCached(long provinceId) {
		ArrayList<District> temp = getCachedDistricts(provinceId);
		if (temp != null) {
			return temp.size() > 0;
		}
		return false;
	}

	public boolean isProvincesCached() {
		return mProvincesCacheMap.size() > 0;
	}

	/**
	 * Is subdistrict cached
	 * 
	 * @param districtId
	 * @return
	 */
	public boolean isSubDistrictsCached(long districtId) {
		ArrayList<SubDistrict> temp = getCachedSubDistricts(districtId);
		if (temp != null) {
			return temp.size() > 0;
		}
		return false;
	}

	/**
	 * Is subproject cached
	 * 
	 * @param subdistrictId
	 * @return
	 */
	public boolean isSubProjectsCached(long subdistrictId) {
		ArrayList<SubProject> temp = getCachedSubProjects(subdistrictId);
		if (temp != null) {
			return temp.size() > 0;
		}
		return false;
	}

	public void putDistricts(long key, ArrayList<District> districts) {
		mDistrictCacheMap.put(key, districts);
	}

	public void putProvince(long key, Province province) {
		mProvincesCacheMap.put(key, province);
	}

	public void putSubDistricts(long key, ArrayList<SubDistrict> SubDistricts) {
		mSubDistrictCacheMap.put(key, SubDistricts);
	}

	public void putSubProjects(long key, ArrayList<SubProject> SubProjects) {
		mSubProjectCacheMap.put(key, SubProjects);
	}

	public boolean removeDistricts(long key) {
		return (mDistrictCacheMap.remove(key) != null);
	}

	public Province removeProvince(long key) {
		return mProvincesCacheMap.remove(key);
	}

	public boolean removeSubDistricts(long key) {
		return (mSubDistrictCacheMap.remove(key) != null);
	}

	public boolean removeSubProjects(long key) {
		return (mSubProjectCacheMap.remove(key) != null);
	}
}
