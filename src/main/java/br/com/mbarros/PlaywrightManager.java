package br.com.mbarros;

import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Playwright;

/**
 * The PlaywrightManager class is responsible for managing the Playwright instances and API request contexts.
 * It provides methods to get the Playwright instance, close the Playwright instance, and dispose of the API request context.
 */
public class PlaywrightManager {

    public static ThreadLocal<Playwright> playwright = new ThreadLocal<>();
    public static ThreadLocal<APIRequestContext> apiRequestContext = new ThreadLocal<>();

    /**
     * Gets the Playwright instance for the current thread. If not already created, a new instance is created.
     *
     * @return The Playwright instance.
     */
    public static Playwright getPlaywright() {
        if (playwright.get() == null) {
            playwright.set(Playwright.create());
        }
        return playwright.get();
    }

    /**
     * Closes the Playwright instance for the current thread, if it exists.
     */
    public static void closePlaywright() {
        Playwright pw = playwright.get();
        if (pw != null) {
            pw.close();
            playwright.remove();
        }
    }

    /**
     * Disposes of the API request context for the current thread, if it exists.
     */
    public static void closeAPIRequestContext() {
        APIRequestContext context = apiRequestContext.get();
        if (context != null) {
            context.dispose();
        }
    }
}