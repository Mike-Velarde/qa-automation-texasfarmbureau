package config;

/**
 * Probably needs a more accurate name
 *
 * This class keeps all constants (names, key values, resource locations, etc.) which Appium needs either to find a resource or send a command to the device
 *
 * Created by ford.arnett on 8/31/15.
 */
public class ResourceLocator {

    /**
    * Drawer IDs
    */
    public static final String DRAWER_HOME_TEXT = "Home";
    public static final String DRAWER_FIND_TEXT = "Find";
    public static final String DRAWER_MENU_TEXT = "Menu";
    public static final String DRAWER_ORDERING_TEXT = "Ordering";
    public static final String DRAWER_CATERING = "Catering";
    public static final String DRAWER_PAY_TEXT = "Pay";
    public static final String DRAWER_SIGN_IN_TEXT = "Sign In";
    public static final String DRAWER_SETTINGS = "Settings";
    public static final String DRAWER_SIGN_OUT = "Sign Out";
    public static final String DRAWER_ABOUT = "About Chick-f​il-A";

    /**
     * Top bar icons, mostly duplicates of the drawer except for more options
     */
    public static final String TOP_BAR_MORE_OPTIONS_ID = "More options";
    public static final String TOP_BAR_ENABLE_STAGING_W2GI_TEXT = "Enable Staging W2GI";
    public static final String TOP_BAR_DISABLE_STAGING_W2GI_TEXT = "Disable Staging W2GI";

    /**
     * Begin pay, the area of the app to
     */
    /**
     * IDs on the Add Card page
     */
    public static final String PAY_ADD_CARD_NAME_ON_CARD = "Name on Card";
    public static final String PAY_ADD_CARD_CARD_NUMBER = "Card Number";
    public static final String PAY_ADD_CARD_TYPE_CHOICE_AMEX  = "American Express";
    public static final String PAY_ADD_CARD_TYPE_CHOICE_DISCOVER = "Discover";
    public static final String PAY_ADD_CARD_TYPE_CHOICE_MASTER_CARD = "Master Card";
    public static final String PAY_ADD_CARD_TYPE_CHOICE_VISA = "Visa";
    public static final String PAY_ADD_CARD_EXPIRATION_DATE = "Expiration Date";
    public static final String PAY_ADD_CARD_SECUITY_CODE = "Security Code";
    public static final String PAY_ADD_CARD_ZIP = "Zip";
    public static final String PAY_ADD_CARD_SAVE_CARD = "Save Card";
    public static final String PAY_ADD_CARD_ADD_CARD_AND_PAY_NOW = "Add Card and Pay Now";
    public static final String PAY_ADD_CARD_AND_PAY_NOW = "Pay Now";


    /**
     * Add funds/payment Ids
     */
    //This is used on the add funds page and the choose amount of funds page
    public static final String PAY_ADD_FUNDS_BUTTON = "add_funds_button";
    public static final String PAYMENT_MANAGE_CARDS_ID = "manage_cards";
    public static final String PAY_VIEW_HISTORY_ID = "view_transactions_btn";
    public static final String PAY_GIFT_CARD_NUMBER_INPUT = "giftcard_number_input";
    public static final String PAY_GIFT_CARD_PIN_INPUT = "giftcard_pin_input";
    public static final String PAY_GIFT_CARD_TRANSFER_BUTTON = "giftcard_transfer_button";

    /**
    * The area of the app to find CFA restaurants, found under the find heading
    */
    public static final String FIND_STORE_DETAILS = "store_container";
    public static final String FIND_MAP_TOGGLE = "menu_sel_location_map_list_toggle";
    public static final String FIND_FILTER = "menu_sel_location_filter";
    public static final String FIND_FILTER_OPTIONS = "ext_list_item_name";
    public static final String FIND_FILTER_OPTIONS_DRIVE_THRU = "Drive-Thru";
    public static final String FIND_FILTER_OPTIONS_WIFI = "WiFi";
    public static final String FIND_FILTER_OPTIONS_PLAYGROUND = "Playground";
    public static final String FIND_FILTER_OPTIONS_MOBILE_ORDERING = "Mobile Ordering";
    public static final String FIND_FILTER_CLOSE = "ext_filter_button";
    public static final String FIND_STORE_DETAILS_PLAYER = "loc_detail_frag_player_video_preview";

    /**
     * The area of the app which is a menu of all food items, found under the menu heading
     */
    public static final String MENU_FOOD_TOP_LEVEL_TILES = "cat_img";
    public static final String MENU_FOOD_SECOND_LEVEL_TILES = "cat_container";
    public static final String MENU_FOOD_NUTRITION_IMG = "item_img";
    public static final String MENU_FOOD_ALLERGENS = "menu_sel_allergens";
    public static final String MENU_FOOD_ALLERGENS_FILTER_OPTIONS = "ext_list_item_name";
    public static final String MENU_FOOD_ALLERGENS_OPTIONS_DAIRY = "Dairy";
    public static final String MENU_FOOD_ALLERGENS_OPTIONS_WHEAT = "Wheat";
    public static final String MENU_FOOD_ALLERGENS_OPTIONS_SOY = "Soy";
    public static final String MENU_FOOD_ALLERGENS_OPTIONS_EGG = "Egg";
    public static final String MENU_FOOD_ALLERGENS_OPTIONS_FISH = "Fish";
    public static final String MENU_FOOD_ALLERGENS_OPTIONS_TREENUT = "Treenut";
    public static final String MENU_FOOD_ALLERGENS_FILTER_CLOSE = "ext_filter_button";
    public static final String MENU_FOOD_ALLERGENS_FILTER_HEADER = "filter_header";

    /**
     * The area of the app to order food, found under the order heading
     */
    public static final String ORDERING_RESTAURANT_CHOOSER_ID = "location_container";
    public static final String ORDERING_RESTAURANT_MENU_SEARCH_ID = "menu_search";
    public static final String ORDERING_CHOOSE_PICKUP_DINE_IN_ID = "dinein_container";
    public static final String ORDERING_CHOOSE_PICKUP_CURBSIDE_ID = "curbside_container";
    public static final String ORDERING_CHOOSE_PICKUP_CARRYOUT_ID = "carryout_container";
    public static final String ORDERING_START_ORDER_BUTTON_ID = "start_order_button";
    public static final String ORDERING_MENU_CATEGORY_LEVEL_ID = "submenu_img";
    public static final String ORDERING_MENU_SECOND_LEVEL_ID = "menu_item_img";
    public static final String ORDERING_MENU_DRINK_CHOICE_ID = "modifier_item_img";
    public static final String ORDERING_STORE_DETAILS_ID = "store_container";
    public static final String ORDERING_ADD_TO_MEAL_ID = "combo_selection_done";
    public static final String ORDERING_ADD_MEAL_TO_ORDER_ID = "combo_sales_item_add_button";
    public static final String ORDERING_ANYTHING_ELSE_DONE_BUTTON_ID = "combo_selection_done";
    public static final String ORDERING_ORDER_SUBMIT_BUTTON_ID = "button_submit";
    public static final String ORDERING_ADD_PAYMENT_INFO_ID = "cart_payment_layout";
    public static final String ORDERING_USE_MY_PAY_ACCOUNT_ID = "choose_payment_item";
    public static final String ORDERING_IM_HERE_ID = "arrived";
    public static final String ORDERING_ORDER_DONE_ID = "combo_selection_done";

    //CFA lab 236 is used to test ordering
    public static final String CFA_LAB_ZIP = "30349";
    public static final String CFA_LAB_236_NUMBER = "CFA Lab 236";
    public static final String CFA_LAB_170_NUMBER = "CFA Lab 170";
    public static final String CFA_LAB_248_NUMBER = "CFA Lab 248";

    /**
     * The area of the app where catering is done, found under the catering heading
     */
    public static final String CATERING_COVER_PHOTO_ID = "cover-photo";
    public static final String ENTER_ADDRESS_FIELD_DESC = "Enter City, State or Zip Code";


    /**
     * The area of the app where the about section is located, found under the about heading
     */
    public static final String ABOUT_WEB_CONTENT_LANDING_ID = "web_view_content_img";

    /**
     * The area of the app for settings, found under settings heading
     */
    public static final String SETTINGS_GET_HELP = "Get Help";
    public static final String SETTINGS_GIVE_FEEDBACK = "Give Feedback";
    public static final String SETTINGS_LEGAL_INFO = "more_card_legal_info";
    public static final String SETTINGS_PRIVACY_POLICY = "more_card_privacy_policy";
    public static final String SETTINGS_LEGAL_TITLE = "Legal";
    public static final String SETTINGS_PRIVACY_TITLE = "Privacy";
    public static final String SETTINGS_USER_ACCOUNT_BUTTON = "image_view_background";
    public static final String SETTINGS_EDIT_USER_PROFILE = "profile_rof_edit_profile_button";
    public static final String SETTINGS_EDIT_USER_PROFILE_DONE = "menu_profile_save";


    /**
     * ADB key events. http://stackoverflow.com/questions/7789826/adb-shell-input-events
     */
    public static final int KEYCODE_0 = 7;
    public static final int KEYCODE_DPAD_RIGHT = 22;
    public static final int KEYCODE_ENTER = 66;
    public static final int KEYCODE_DEL = 67;

}
