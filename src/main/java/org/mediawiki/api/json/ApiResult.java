package org.mediawiki.api.json;

import com.github.kevinsawicki.http.HttpRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Encapsulates the result of performing an API call.
 *
 * The network request is made *only* when {@link #asArray()} or {@link #asObject()}
 * is called.
 */
public class ApiResult {
    /**
     * Request for which this object holds the results.
     */
    private final HttpRequest request;

    /**
     * Create an APIResult object corresponding to this request object.
     *
     * @param request {@link HttpRequest} object which can perform the query for which this object holds the results.
     */
    ApiResult(final HttpRequest request) {
        this.request = request;
    }

    /**
     * Disconnect the request if it is in progress.
     */
    public void cancel() {
        request.disconnect();
    }

    private JSONArray resultArray;
    /**
     * Start the network request & return the response as a JSON Array.
     * Locally cache the result too, so multiple calls to this method will succeeed.
     *
     * Use this when a JSON Array is returned by the API. So far, only action=opensearch.
     *
     * @return A {@link JSONArray} object with the results of the API query.
     * @throws ApiException Thrown in the case of a network error, or if the response is not a JSON Array.
     */
    public JSONArray asArray() throws ApiException {
        try {
            if (resultArray == null) {
                resultArray = new JSONArray(request.body());
            }
            return resultArray;
        } catch (JSONException e) {
            throw new ApiException(e);
        } catch (HttpRequest.HttpRequestException e) {
            throw new ApiException(e.getCause());
        }
    }

    private JSONObject resultObject;
    /**
     * Start the network request & return the response as a JSON Object.
     * Locally cache the result too, so multiple calls to this method will succeeed.
     *
     * @return A {@link JSONObject} object with the results of the API query.
     * @throws ApiException Thrown in the case of a network error, or if the response is not a JSON Object.
     */
    public JSONObject asObject() throws ApiException {
        try {
            if (resultObject == null) {
                resultObject = new JSONObject(request.body());
            }
            return resultObject;
        } catch (JSONException e) {
            throw new ApiException(e);
        } catch (HttpRequest.HttpRequestException e) {
            throw new ApiException(e.getCause());
        }
    }
}
