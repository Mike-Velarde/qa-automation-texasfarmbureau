package config;

/**
 * Created by ford.arnett on 11/3/15.
 */
public class ResourceLocatorIos extends ResourceLocator{
    public static final String AWE_MAIN_DRAWER_CLOSE_STATE = "nb grid close";
    public static final String AWE_MAIN_DRAWER_OPEN_STATE = "nb grid open";
    public static final String AWE_MAIN_OVERLAY_CLOSE = "nb close x";
    public static final String AWE_MAIN_TITLE_XPATH = "//UIAApplication[1]/UIAWindow[1]/UIAStaticText[1]";
    public static final String AWE_SHOW_DEATILS_NAV_BACK_FEATURED = "Featured";

    public static final String AWE_SHOWS_SEASON_ARROW = "season_arrow";
    public static final String AWE_SHOWS_SEASONS_TABLE_VIEW = "Empty list";

    public static final String AWE_SHOWS_EPISODE_OR_CLIPS_BUTTONS_XPATH = "//UIAApplication[1]/UIAWindow[1]/UIACollectionView[1]/UIAButton";

    public static final String UIA_KEYBOARD = "UIA_KEYBOARD";
    public static final String UIA_BUTTON = "UIAButton";
    public static final String UIA_TABLE_CELL = "UIATableCell";



    {
        /**
         * Awe main toolbar
         */
        AWE_MAIN_TOOLBAR_SEARCH = "nb search";
        AWE_MAIN_TOOLBAR_SHARE = "nb share";
        AWE_MAIN_TOOLBAR_WATCHLIST = "nb watchlist";

        AWE_MAIN_DRAWER = "nb grid close";

        /**
         * Login
         */
        AWE_SETTINGS_LOGIN_LOGOUT_TEXT = "Log In To Provider";
        AWE_LOGIN_CONTINUE = "Continue";

        /**
         * Nav items
         */
        ResourceLocator.DrawerNavigationItem.featured.navigationItemDesc = "nb featured";
        ResourceLocator.DrawerNavigationItem.shows.navigationItemDesc = "nb shows";
        ResourceLocator.DrawerNavigationItem.movies.navigationItemDesc = "nb movies";
        ResourceLocator.DrawerNavigationItem.watchlist.navigationItemDesc = "nb watchlist";
        ResourceLocator.DrawerNavigationItem.schedule.navigationItemDesc = "nb schedule";
        ResourceLocator.DrawerNavigationItem.settings.navigationItemDesc = "nb settings";
        ResourceLocator.DrawerNavigationItem.feeds.navigationItemDesc = "nb feeds";

        AWE_VIDEO_DETAILS_PLAY_BUTTON = "btn invideo play";

        WEBKIT_WEBVIEW = "UIAWebView";

        AWE_SHOWS_CONTAINER_GRID = "//UIAApplication[1]/UIAWindow[1]/UIACollectionView[2]";

        AWE_SHOW_DETAILS_ADD_TO_WATCHLIST = "Watchlist";
        AWE_SHOW_DETAILS_REMOVE_FROM_WATCHLIST = "Watchlist";

        AWE_MAIN_TOOLBAR_MORE_OPTIONS = "nb grid close";
        AWE_MAIN_TOOLBAR_SHARE_OVERFLOW = "nb share";
    }

}
