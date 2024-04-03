package gorest.api.constants;

import gorest.api.utils.R;

public class ConfigConstant {

    public static final String BASE_PATH = "src/test/resources/test_data/rest";

    public static final String DEFAULT_API_URL = R.CONFIG.getString("api_url");
    public static final String API_ACCESS_TOKEN = R.CONFIG.getString("access_token");

}
