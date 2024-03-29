package Lesson03;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class C05_Alerts {

    /*
   Bir web sitesine girdiğimizde karşımıza bir uyarı mesayı yada bir buttona tıkladığımızda bir uyarı(alert)
   çıkabilir. Eğer bu uyarıya incele(mause sağ tik-inspect) yapabiliyorsak bu tür alert'lere HTML alert denir
   ve istediğimiz locate'i alabiliriz. Ama gelen uyarı kutusuna müdehale(Sağ tik-incele) edemiyorsak bu tür
   alert'lere js alert denir. js alert'lere müdehale edebilmek için;

   - Tamam yada ok butonuna tıklamak için --> driver.switchTo().alert().accept()
   - Iptal butonuna tıklamak için --> driver.switchTo().alert().dismiss()
   - Alert içindeki mesajı almak için --> driver.switchTo().alert().getText()
   - Alert bizden bir metin istiyorsa --> driver.switchTo().alert().sendKeys("")

   kullanılır.
    */
    WebDriver driver;

    @Before
    public void setUp() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        //https://the-internet.herokuapp.com/javascript_alerts adresine gidin.
        driver.get("https://the-internet.herokuapp.com/javascript_alerts");

    }

    @After
    public void tearDown() {
        // driver.quit();
    }

    @Test

    public void accpetAlert() throws InterruptedException {
        //1. butona tıklayın, uyarıdaki OK butonuna tıklayın ve result mesajının  “You successfully clicked an alert” oldugunu test edin.
        driver.findElement(By.xpath("//*[text()='Click for JS Alert']")).click();
        Thread.sleep(2000);
        driver.switchTo().alert().accept();
        WebElement resulttext= driver.findElement(By.xpath("//*[text()='You successfully clicked an alert']"));
        String actualText=resulttext.getText();
        String expectedText="You successfully clicked an alert";
        Assert.assertEquals(expectedText,actualText);
    }

    @Test

    public void dismissAlert() throws InterruptedException {
        //2. butona tıklayın, uyarıdaki Cancel butonuna tıklayın ve result mesajının
        //“successfuly” icermedigini test edin. Alert üzerindeki mesajı yazdırınız!
        driver.findElement(By.xpath("//*[text()='Click for JS Confirm']")).click();
        Thread.sleep(2000);
        System.out.println("Aletin içindeki mesaj: "+driver.switchTo().alert().getText());
        Thread.sleep(2000);
        driver.switchTo().alert().dismiss();
        Thread.sleep(2000);
        String resulMessage=(driver.findElement(By.xpath("//*[text()='You clicked: Cancel']"))).getText();
        String expectedMessage="succesfully";
        Assert.assertFalse(resulMessage.contains(expectedMessage));
        Thread.sleep(2000);
    }

    @Test

    public void sendKeysAlert() throws InterruptedException {
        //3. butona tıklayın, uyarıdaki metin kutusuna isminizi yazin, OK butonuna
        //tıklayın ve result mesajında isminizin görüntülendiğini doğrulayın.
        driver.findElement(By.xpath("//*[text()='Click for JS Prompt']")).click();
        driver.switchTo().alert().sendKeys("Ali");
        Thread.sleep(2000);
        driver.switchTo().alert().accept();
        Thread.sleep(2000);
        String resultMessage=driver.findElement(By.xpath("//p[@id='result']")).getText();
        String expectedMessage="Ali";
        Assert.assertTrue(resultMessage.contains(expectedMessage));
    }
}
