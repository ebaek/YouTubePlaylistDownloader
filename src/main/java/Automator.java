import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Automator {

    public static void convertVideo(WebDriver driver, String url) {
        String converterSite = "https://ytmp3.cc/en2/";
        driver.get(converterSite);

        // Paste url
        WebElement searchBar = driver.findElement(By.cssSelector("input#input"));
        searchBar.sendKeys(url);

        // Click download
        WebElement convertButton = driver.findElement(By.cssSelector("input#submit"));
        convertButton.click();

        // Wait until finished converting video
        WebDriverWait wait = new WebDriverWait(driver, 5000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Download")));

        // Download
        driver.findElement(By.linkText("Download")).click();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Paste playlist url: ");
        String playlistUrl = scanner.nextLine();

        WebDriver youTubeDriver = new ChromeDriver();
        WebDriver dlDriver = new ChromeDriver();

        try {
            youTubeDriver.get(playlistUrl);
            Thread.sleep(2000);

            String videosSelector = "a.yt-simple-endpoint.style-scope.ytd-playlist-video-renderer";

            // Select playlist videos
            List<WebElement> playlistVideos = youTubeDriver.findElements(By.cssSelector(videosSelector));
            Iterator<WebElement> videoIter = playlistVideos.iterator();

            while(videoIter.hasNext()) {
                WebElement video = videoIter.next();

                // Retrieve video url
                String url = video.getAttribute("href");

                convertVideo(dlDriver, url);
            }

            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            youTubeDriver.quit();
            dlDriver.quit();
        }
    }
}
