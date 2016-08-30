package config;

/**
 * Created by ford.arnett on 11/3/15.
 */
public class ResourceLocatorIos extends ResourceLocator{
    /**
     * Main
     */
    public static final String AWE_MAIN_DRAWER_CLOSE_STATE = "Grid Menu Button - Closed";
    public static final String AWE_MAIN_DRAWER_OPEN_STATE = "Grid Menu Button - Open";
    public static final String AWE_MAIN_OVERLAY_CLOSE = "nb close x";
    public static final String AWE_MAIN_TITLE_XPATH = "//UIAApplication[1]/UIAWindow[1]/UIAStaticText[1]";

    //Currently all the buttons share this same name,but the buttons contain an image with a name. This can be used to distinguish them further
    public static final String MAIN_TOOLBAR_GENERIC_NAME = "awe_gridmenu_itemtint";



    /**
     * Shows
     */
    public static final String AWE_SHOWS_SEASON_ARROW = "season_arrow";
    public static final String AWE_SHOWS_SEASONS_TABLE_VIEW = "Empty list";
    public static final String AWE_SHOWS_SEASON_SELECT_OPTIONS = "awe_assetfilter_seasonselectorcell";
    public static final String AWE_SHOWS_SEASON_SELECT_HEADER = "Season";
    public static final String AWE_SHOWS_LISTING_CONTAINER = "AWEMediaViewController";
    public static final String AWE_SHOWS_MEDIA_CONTAINER = "AWEMediaContainerDetailViewController";
    public static final String AWE_SHOWS_SHARE_SHOW_BUTTON = "Share";
    public static final String IOS_SHARE_APPS_FACEBOOK = "Facebook";
    public static final String FACEBOOK_SHARE_SHOW_POST_BUTTON = "Post";

    /**
     * Show Details, a carousel with show details which pops up after tapping on a show
     */
    public static final String AWE_SHOW_DETAILS_NAV_BACK_FEATURED = "awe_global_backbuttontint";
    public static final String AWE_SHOW_DETAIL_ASSET_DETAIL_CONTAINER = "awe_assetdetail_showtitlelabel";
    public static final String AWE_SHOW_DETAIL_ASSET_DETAIL_TITLE = "awe_assetdetail_titlelabel";
    public static final String AWE_SHOW_DETAIL_ASSET_DETAIL_PLAY_BUTTON = "awe_assetdetail_playbutton";
    public static final String AWE_SHOW_DETAIL_ASSET_DETAIL_SEASON_INFO = "awe_assetdetail_seasoninfolabel";
    public static final String AWE_SHOW_DETAIL_ASSET_DETAIL_AIRING_INFO = "awe_assetdetail_airinginfolabel";
    public static final String AWE_SHOW_DETAIL_ASSET_DETAIL_DESCRIPTION = "awe_assetdetail_descriptionlabel";
    public static final String AWE_SHOW_DETAIL_ASSET_DETAIL_EXPIRATION = "awe_assetdetail_availableuntillabel";
    public static final String AWE_SHOW_DETAIL_ASSET_DETAIL_THUMBNAIL = "Video thumbnail";

    //CV = Collection View
    public static final String AWE_SHOW_DETAIL_ASSET_DETAIL_CV_PARENT = "AWEAssetDetailCollectionViewController";


    public static final String AWE_WATCHLIST_QUEUE_MAIN_CONTAINER_IPAD = "AWEWatchlistQueueViewController_iPad";
    public static final String AWE_WATCHLIST_MAIN_CONTAINER_TO_SHOW_CELLS_XPATH = "//UIACollectionView[1]/UIACollectionCell";
    public static final String AWE_WATCHLIST_EPISODE_CONTAINERS = "awe_watchlist_assetcelltitlelabel";
    public static final String AWE_WATCHLIST_CONTINUE_MAIN_CONTAINER = "AWEWatchlistContinueWatchingViewController";

    public static final String AWE_SHOWS_EPISODE_OR_CLIPS_BUTTONS_XPATH = "//UIAApplication[1]/UIAWindow[1]/UIACollectionView[1]/UIAButton";

    public static final String AWE_SCHEDULE_ALL_DAYS_CONTAINER = "AWEScheduleVerticalViewController";
    public static final String AWE_SCHEDULE_DAYS_XPATH = "//UIAApplication[1]/UIAWindow[1]/UIAElement[2]/UIAElement[1]/UIAElement[1]/UIACollectionView[1]/UIACollectionCell[*]";

    /**
     * iOS Classes and Constants
     */
    public static final String UIA_KEYBOARD = "UIA_KEYBOARD";
    public static final String UIA_BUTTON = "UIAButton";
    public static final String UIA_TABLE_CELL = "UIATableCell";
    public static final String UIA_TEXT_FIELD = "UIATextField";
    public static final String UIA_SECURE_TEXT_FIELD = "UIASecureTextField";
    public static final String UIA_COLLECTION_CELL = "UIACollectionCell";
    public static final String UIA_COLLECTION_VIEW = "UIACollectionView";
    public static final String UIA_WINDOW = "UIAWindow";

    /**
     * Settings
     */
    public static final String AWE_SETTINGS_LOGIN_BUTTON_ID = "Log In To Provider";
    public static final String AWE_SETTINGS_LOGOUT_BUTTON_ID = "Log Out From Provider";
    public static final String AWE_SETTINGS_EMAIL_FORM_SEND = "Send";

    public static final String AWE_VIDEO_PLAYER_PAUSE = "Pause";
    public static final String AWE_VIDEO_PLAYER_PLAY = "Play";
    public static final String AWE_SETTINGS_EMAIL_DELETE_DRAFT = "Delete Draft";
    public static final String AWE_SETTINGS_EMAIL_HEADER = "USA Now App Feedback";
    public static final String AWE_SETTINGS_EMAIL_SUBJECT = "subjectField";


    {
        /**
         * Awe main toolbar
         */
        AWE_MAIN_TOOLBAR_SEARCH = "Search Menu Item";
        AWE_MAIN_TOOLBAR_SHARE = "nb share";
        AWE_MAIN_TOOLBAR_WATCHLIST = "Watchlist Menu Item";

        AWE_MAIN_DRAWER = "awe_gridmenu_buttontint";

        /**
         * Login
         */
        AWE_SETTINGS_LOGIN_LOGOUT_TEXT = "Log In To Provider";
        AWE_LOGIN_CONTINUE = "Continue";

        /**
         * Nav items
         */
        DrawerNavigationItem.featured.navigationItemDesc = "NOT ON IOS";
        DrawerNavigationItem.shows.navigationItemDesc = "Shows Menu Item";
        DrawerNavigationItem.movies.navigationItemDesc = "NOT ON IOS";
        DrawerNavigationItem.watchlist.navigationItemDesc = "Watchlist Menu Item";
        DrawerNavigationItem.schedule.navigationItemDesc = "Schedule Menu Item";
        DrawerNavigationItem.search.navigationItemDesc = "Search Menu Item";
        DrawerNavigationItem.settings.navigationItemDesc = "Settings Menu Item";
        DrawerNavigationItem.feeds.navigationItemDesc = "NOT ON IOS";
        DrawerNavigationItem.live.navigationItemDesc = "Live Menu Item";

        CallsToAction.details.callToAction = "AWECTAIconActionTypeShowDetailIdentifier";
        CallsToAction.watchlist.callToAction = "AWECTAIconActionTypeWatchListAddIdentifier";
        CallsToAction.play.callToAction = "AWECTAIconActionTypeVideoDetailIdentifier";
        CallsToAction.website.callToAction = "AWECTAIconActionTypeWebIdentifier";

        AWE_VIDEO_DETAILS_PLAY_BUTTON = "awe_assetdetail_playbutton";

        WEBKIT_WEBVIEW = "UIAWebView";

        AWE_SHOWS_CONTAINER_GRID = "//UIAApplication[1]/UIAWindow[1]/UIACollectionView[2]";

        AWE_SHOW_DETAILS_ADD_TO_WATCHLIST = "Watchlist";
        AWE_SHOW_DETAILS_REMOVE_FROM_WATCHLIST = "Watchlist";
        AWE_SHOW_DETAILS_CONTAINER = "AWEMediaContainersViewController";
        AWE_SHOW_DETAILS_ADD_TO_WATCHLIST = "AWEMediaContainerDetailWatchlistAddImageIdentifier";
        AWE_SHOW_DETAILS_REMOVE_FROM_WATCHLIST = "AWEMediaContainerDetailWatchlistRemoveImageIdentifier";

        AWE_SETTINGS_FEEDBACK_TITLE_ID = "Feedback";

        AWE_MAIN_TOOLBAR_MORE_OPTIONS = "nb grid close";
        AWE_MAIN_TOOLBAR_SHARE_OVERFLOW = "nb share";

        AWE_SEARCH_RESULTS = "awe_search_celltitlelabel";

        AWE_VIDEO_PLAYER_ROOT = "AWEVideoTransportControlsViewController";

    }

}
