package config;

import operations.AutomationOperations;
import org.openqa.selenium.By;

/**
 * Probably needs a more accurate name
 *
 * This class keeps all constants (names, key values, resource locations, etc.) which Appium needs either to find a resource or send a command to the device
 *
 * Created by ford.arnett on 8/31/15.
 */
public class ResourceLocator {
    public static ResourceLocator device = AutomationOperations.instance().deviceAutomationComponents.getResourceLocator();

    //This doesn't really have a home right now, so it lives here for now
    //Seconds = Time * 1000
    public static final int AWE_INITIAL_ADS_WAIT_TIME = 25000;

    /**
    * AWE brands
    */
    public String AWE_BRAND_NAMES_AWE_AUTOMATION = "awe_automation";
    public String AWE_BRAND_NAMES_AWE_BET = "bet";
    public String AWE_BRAND_NAMES_AWE_BRAVO = "bravo";
    public String AWE_BRAND_NAMES_COOKINGCHANNEL = "cookingchannel";
    public String AWE_BRAND_NAMES_DIYNETWORK = "diynetwork";
    public String AWE_BRAND_NAMES_EONLINE = "eonline";
    public String AWE_BRAND_NAMES_ESQUIRE = "esquire";
    public String AWE_BRAND_NAMES_FOODNETWORK = "foodnetwork";
    public String AWE_BRAND_NAMES_HALLMARKCHANNEL = "hallmarkchannel";
    public String AWE_BRAND_NAMES_HGTV = "hgtv";
    public String AWE_BRAND_NAMES_MSNBC = "msnbc";
    public String AWE_BRAND_NAMES_MUN2 = "mun2";
    public String AWE_BRAND_NAMES_NBCE = "nbce";
    public String AWE_BRAND_NAMES_OWN = "own";
    public String AWE_BRAND_NAMES_OXYGEN = "oxygen";
    public String AWE_BRAND_NAMES_SPROUT = "sprout";
    public String AWE_BRAND_NAMES_SYFY = "syfy";
    public String AWE_BRAND_NAMES_TASTEMADE = "tastemade";
    public String AWE_BRAND_NAMES_TELEMUNDO = "telemundo";
    public String AWE_BRAND_NAMES_TRAVELCHANNEL = "travelchannel";
    public String AWE_BRAND_NAMES_USA = "usa";

    /**
     * AWE feeds
     */
    public String AMP_MERA_DEV_LIVE = "AMP MERA Dev-Live";
    public String AMP_MERA_DEV_PREVIEW = "AMP MERA Dev-Preview";
    public String ANDROID_FW_DEV_LIVE = "Android FW Dev-Live";
    public String ANDROID_FW_DEV_PREVIEW = "Android FW Dev-Preview";
    public String ANDROID_FW_QA_LIVE = "Android FW QA-Live";
    public String ANDROID_FW_QA_PREVIEW = "Android FW QA-Preview";
    public String ANDROID_IMP_STAGE_LIVE = "Android Imp Stage-Live";
    public String ANDROID_IMP_STAGE_PREVIEW = "Android Imp Stage-Preview";
    public String ANDROID_IMP_DEV_LIVE = "Android Imp Dev-Live";
    public String ANDROID_IMP_DEV_PREVIEW = "Android Imp Dev-Preview";
    public String ANDROID_IMP_QA_LIVE = "Android Imp QA-Live";
    public String ANDROID_IMP_QA_PREVIEW = "Android Imp QA-Preview";
    public String JCP_DEV_LIVE = "JCP Dev-Live";
    public String JCP_DEV_PREVIEW = "JCP Dev-Preview";
    public String NBC_AWE3_PROD_EP1_LIVE = "NBC AWE3 Prod-EP1-Live";
    public String NBC_AWE3_PROD_EP2_LIVE = "NBC AWE3 Prod-EP2-Live";
    public String NBC_AWE3_PROD_PREVIEW = "NBC AWE3 Prod-Preview";
    public String NBC_AWE3_STAGE_EP1_LIVE = "NBC AWE3 Stage-EP1-Live";
    public String NBC_AWE3_STAGE_EP2_LIVE = "NBC AWE3 Stage-EP2-Live";
    public String NBC_AWE3_STAGE_PREVIEW = "NBC AWE3 Stage-Preview";
    public String WFG_DEV_LIVE = "WFG Dev-Live";
    public String WFG_DEV_PREVIEW = "WFG Dev-Preview";
    public String IOS_FW_DEV_LIVE = "iOS FW Dev-Live";
    public String IOS_FW_DEV_PREVIEW = "iOS-FW Dev-Preview";
    public String IOS_FW_QA_LIVE = "iOS-FW QA-Live";
    public String IOS_FW_QA_PREVIEW = "iOS-FW QA-Preview";
    public String IOS_FW_STAGE_LIVE = "iOS-FW Stage-Live";
    public String IOS_FW_STAGE_PREVIEW = "iOS-FW Stage-Preview";
    public String IOS_IMP_DEV_LIVE = "iOS Imp Dev-Live";
    public String IOS_IMP_DEV_PREVIEW = "iOS Imp Dev-Preview";
    public String IOS_IMP_QA_LIVE = "iOS Imp QA-Live";
    public String IOS_IMP_QA_PREVIEW = "iOS Imp QA-Preview";
    public String IOS_IMP_STAGE_LIVE = "iOS Imp Stage-Live";
    public String IOS_IMP_STAGE_PREVIEW = "iOS Imp Stage-Preview";


    public String AWE_PICKFEED_SERVERURL_ID = "com.bottlerocketapps.awe.watcher:id/awe_pickfeed_serverurl";

    public String ANDROID_FRAMEWORK_DEV_LIVE_FEED = "http://andfw.dev.bottlerocketservices.com/live/5/usa/config";

    /**
     * ADB key events. http://stackoverflow.com/questions/7789826/adb-shell-input-events
     */
    public int KEYCODE_0 = 7;
    public int KEYCODE_DPAD_RIGHT = 22;
    public int KEYCODE_ENTER = 66;
    public int KEYCODE_DEL = 67;


    /**
     * Awe main toolbar
     */
    public String AWE_MAIN_TOOLBAR = "awe_toolbar_container";
    public String AWE_MAIN_TOOLBAR_TITLE_ID = "awe_actionbar_title";
    public String AWE_MAIN_DRAWER_ANCHOR = "AweWatcher";
    public String AWE_MAIN_DRAWER = "awe_navigation_drawerviewcontainer";
    public String AWE_MAIN_TOOLBAR_PROVIDER_LOGO = "awe_authflow_providerlogo";
    public String AWE_MAIN_TOOLBAR_LIVE = "menu_live";
    public String AWE_MAIN_TOOLBAR_SEARCH = "awe_menu_search";
    public String AWE_MAIN_TOOLBAR_SHARE = "menu_share";
    public String AWE_MAIN_TOOLBAR_WATCHLIST = "menu_watchlist";
    public String AWE_MAIN_TOOLBAR_BACK = "Navigate up";
    public String AWE_MAIN_TOOLBAR_MORE_OPTIONS = "More options";
    public String AWE_MAIN_TOOLBAR_SEARCH_OVERFLOW = "Search";
    public String AWE_MAIN_TOOLBAR_SHARE_OVERFLOW = "Share";

    public String AWE_SEARCH_BAR_ENTER_TEXT = "awe_search_actionprovidertext";
    public String AWE_SEARCH_RESULTS = "awe_search_listitem";
    public String AWE_SEARCH_RESULTS_CONTAINER = "awe_search_list";

    public String AWE_DRAWER_WATCHLIST_COUNT = "awe_navigation_itemcount";

    /**
     * Share
     */
    public String AWE_SHARE_OPTIONS_GOOGLE_PLUS = "Google+";
    public String AWE_SHARE_OPTIONS_FACEBOOK = "Facebook";
    public String AWE_SHARE_OPTIONS_GMAIL = "Gmail";

    public String FACEBOOK_POST_OPTION = "post_button";

    /**
     * Awe featured
     */
    public String AWE_FEATURED_CTA_CONTAINER = "awe_featured_cellactacontainer";
    public String AWE_FEATURED_CAROUSEL_SHOW_TITLE = "awe_featured_cellatitle";
    public String AWE_SHOW_DETAILS_SHOW_TITLE = "awe_showdetail_featuredtitle";

    /**
     * Awe settings
     */
    public String AWE_SETTINGS_LOGIN_LOGOUT = "awe_settings_btnloginout";
    public String AWE_SETTINGS_LOGIN_LOGOUT_TEXT = "awe_settings_btnloginouttext";
    public String AWE_LOGIN_CONTINUE = "awe_auth_welcomecontinue";
    public String AWE_LOGIN_PROVIDER_NOT_LISTED = "awe_auth_picknotlisted";
    public String AWE_AUTH_PROVIDER_CONTAINER = "awe_auth_pickcontentcontainer";

    public String GMAIL_COMPOSE_TITLE = "action_bar_title";
    public String GMAIL_COMPOSE_TEXT = "Compose";

    /**
     * Authentication buttons and fields
     */
    public String CABLE_PROVIDER_IMAGE_ID = "awe_auth_itemwithimageimage";
    public String PROVIDER_LOGIN_USERNAME_ID = "IDToken1";
    public String PROVIDER_LOGIN_PASSWORD_ID = "IDToken2";
    public String PROVIDER_SIGN_IN_BUTTON = "signin_button";
    //The id seems to have been removed from this button and getting all the image views has proven to be unreliable
    public String OPTIMUM_SIGN_IN_XPATH = "//android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.RelativeLayout[1]/android.widget.FrameLayout[1]/android.widget.RelativeLayout[1]/android.widget.RelativeLayout[1]/android.webkit.WebView[1]/android.view.View[1]/android.widget.Image[3]";

    /**
     * Video
     */
    public String AWE_VIDEO_PLAYER_CONTAINER = "awe_videodetail_thumbnailcontainer";
    public String AWE_VIDEO_PLAYER_TIMER = "awe_player_controlstimer";
    public String AWE_VIDEO_PLAYER_PLAY_PAUSE = "awe_player_controlsplaypause";
    public String AWE_VIDEO_PLAYER_ROOT = "awe_player_videocomponentroot";
    public String AWE_VIDEO_LOADING_SPINNER = "awe_global_progressbar";
    public String AWE_VIDEO_PLAYER_AD_COUNTDOWN_BANNER = "awe_player_adcountdowntimer";
    public String AWE_VIDEO_PLAYER_CLOSED_CAPTION_DESC = "Toggle Closed Captions";
    public String AWE_VIDEO_PLAYER_SEEK_BAR = "awe_player_controlsseekbar";

    /**
     * Shows
     */
    public String AWE_SHOWS_CONTAINER_GRID = "awe_containers_grid";
    public String AWE_SHOWS_EPISODES_CLIPS_MAIN_CONTAINER = "awe_showdetail_viewpager";
    public String AWE_SHOWS_VIDEO_THUMBNAILS = "awe_shows_itemimage";

    /**
     * Show details
     */
    public String AWE_SHOW_DETAILS_EPISODE_TAB = "Episodes";
    public String AWE_SHOW_DETAILS_CLIPS_TAB = "Clips";
    public String AWE_SHOW_DETAILS_EPISODE_LIST_CONTAINER = "awe_showdetail_episodelist";
    public String AWE_SHOW_DETAILS_VIDEO_THUMBNAILS = "awe_showdetail_videoitemimage";
    public String AWE_SHOW_DETAILS_EPISODE_EMPTY_MESSAGE_ID = "awe_showdetail_episodeempty";
    public String AWE_SHOW_DETAILS_CLIP_EMPTY_MESSAGE_ID = "awe_showdetail_clipempty";
    public String AWE_SHOW_DETAILS_SEASON_SELECT_HEAD = "awe_showdetail_filterspinner";
    public String AWE_SHOW_DETAILS_SEASON_SELECT_SEASON = "awe_showdetail_seasonspinnertext";
    public String AWE_SHOW_DETAILS_SEASON_TITLE = "awe_seasondropdownlistspinner_title";
    public String AWE_SHOW_DETAILS_ADD_TO_WATCHLIST = "Add To Watchlist"; //legacy menu_add_to_watchlist
    public String AWE_SHOW_DETAILS_REMOVE_FROM_WATCHLIST = "Remove from Watchlist"; //legacy menu_remove_from_watchlist

    /**
     * Schedule
     */
    public String AWE_SCHEDULE_DATE_PAGE_INDICATOR = "awe_schedule_pageindicator";
    public String AWE_SCHEDULE_DATE_HEADINGS = "awe_schedule_datetext";
    public String AWE_SCHEDULE_SHOW_LISTING = "awe_schedule_itemtitlecontainer";

    /**
     * Settings
     */
    public String AWE_SETTINGS_ABOUT_BRAND_TITLE_ID = "awe_settings_btnaboutbrand";
    public String AWE_SETTINGS_FAQ_TITLE_ID = "awe_settings_btnfaq";
    public String AWE_SETTINGS_PRIVACY_POLICY_TITLE_ID = "awe_settings_btn_privacypolicy";
    public String AWE_SETTINGS_TERMS_AND_CONDITIONS_TITLE_ID = "awe_settings_btnterms";
    public String AWE_SETTINGS_FEEDBACK_TITLE_ID = "awe_settings_btnfeedback";
    public String AWE_SETTINGS_ABOUT_BOTTLE_ROCKET_TITLE_ID = "awe_settings_btnaboutbr";
    public String AWE_SETTINGS_DEV_OPTIONS_TITLE_ID = "awe_settings_btndevoptions";
    public String AWE_SETTINGS_DEV_OPTIONS_MAIN_LIST = "awe_devoptions_list";

    public String AWE_SETTINGS_DEV_OPTIONS_TITLE = "Developer Options";

    /**
     * Watchlist
     */
    public String AWE_WATCHLIST_CONTINUE_WATCHING_ROW = "awe_watchlist_tablethistoryrowgallery";
    public String AWE_WATCHLIST_QUEUE_AND_CONTINUE_CONTAINER = "awe_watchlist_tabletlist";
    public String AWE_WATCHLIST_QUEUE_SHOW_ROWS = "awe_watchlist_showitemgallery";
    public String AWE_WATCHLIST_SHOW_IMAGE = "awe_watchlist_itemimage";


    /**
     * Video details page, this page has the details of the video on the side and is shown before the video is played
     */
    public String AWE_VIDEO_DETAILS_PLAY_BUTTON = "awe_videodetail_thumbnailimage";

    /**
     * Cable providers
     */
    public String OPTIMUM_CONTENT_DESC = "Cablevision";


    /**
     * USA Endpoints
     */
    public String USA_ENDPOINT_ABOUT_USA_NOW = "http://www.usanetwork.com/about-usa-anywhere-plus";
    public String USA_ENDPOINT_FAQ = "http://tve-usa.nbcuni.com/nativeapp/help/js/data/faq-nonrwd/tvefaq.html";
    public String USA_ENDPOINT_PRIVACY_POLICY = "http://www.nbcuniversal.com/privacy/mobile-apps";
    public String USA_ENDPOINT_TERMS_AND_CONDITIONS = "http://www.usanetwork.com/terms";

    public String BR_ENDPOINT_ABOUT_BR = "http://www.bottlerocketapps.com/brAbout";


    //Use this to allow subclasses to overwrite the enum's string
    //public abstract void initCallsToAction();

    public enum DrawerNavigationItem {
        featured("Featured"),
        shows("Shows"),
        movies("Movies"),
        watchlist("Watchlist"),
        schedule("Schedule"),
        settings("Settings"),
        feeds("Feeds");

        String navigationItemDesc;
        DrawerNavigationItem(String s){
            navigationItemDesc = s;
        }

        public String toString(){
            return navigationItemDesc;
        }
    }

    public enum CallsToAction {
        details("DETAILS"),
        watchlist("WATCHLIST"),
        play("PLAY"),
        website("WEBSITE");

        String callToAction;
        CallsToAction(String s) { callToAction = s; }

        public String toString() { return callToAction; }
    }

    /**
     * View classes
     */
    public String LINEAR_LAYOUT = "android.widget.LinearLayout";
    public String FRAME_LAYOUT = "android.widget.FrameLayout";
    public String EDIT_TEXT = "android.widget.EditText";
    public String IMAGE_VIEW = "android.widget.Image";
    public String WEBKIT_WEBVIEW = "android.webkit.WebView";

}
