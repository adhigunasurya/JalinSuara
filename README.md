Jalin Suara Android


Website
	http://jalinsuara.web.id/en

	https://www.facebook.com/PNPMsupport
	https://www.facebook.com/jalin.suara
	@jalin_suara
	@PNPM_support
	
Preparing environment 
1. Eclipse
2. Android SDK
3. Import projects:
	- actionbarsherlock as android library project
	- JalinSuara
	- JalinSuaraTest
	- google-play-services_lib as android library project

Package
com.jalinsuara.android

Convention

Java 
	field's prefix = m
	resource file name prefix
		activity_<name>.xml
		fragment_<name>.xml
		layout_<name>.xml
		list_item_<name>.xml
	
resource id prefix = file name prefix + <name> + <View type>
	e.g.
	in activity_login.xml
		id textview for username 
			@+id/activity_login_username_textview
			
for communication between activity, use intent.
	create final static field EXTRA_<extra name>

Use BaseActivity, BaseFragment, or etc when creating new class

Use resetStatus() and setStatus...() for showing and dismissing progress bar in activity or fragment.

Use JalinSuaraSingleton for accessing data across activity.

Use getString(string res id) for showing text.




