package br.com.elvisoliveira.contactsfinder;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;

public class busca extends HttpServlet {

    private String baseUrl;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
        PhantomJSDriver driver = new PhantomJSDriver(capabilities);
        // driver = new FirefoxDriver();

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        // manual selection
        // driver.get("http://www.telelistas.net/");
        // driver.findElement(By.id("nome")).clear();
        // driver.findElement(By.id("nome")).sendKeys("john");
        // new Select(driver.findElement(By.id("uf_busca"))).selectByVisibleText("MG");
        // new Select(driver.findElement(By.id("pch_localidade_select"))).selectByVisibleText("Belo Horizonte");
        // driver.findElement(By.name("image")).click();
        // automatic selection
        String orgm = "0";
        String cod_localidade = "31000";
        String atividade = "";
        String nome = URLEncoder.encode("Sebastião", "ISO-8859-1");
        String uf_busca = "mg";
        String imagex = "0";
        String imagey = "0";

        String url = String.format("http://www.telelistas.net/templates/resultado_busca.aspx?q=&orgm=%s&cod_localidade=%s&atividade=%s&nome=%s&uf_busca=%s&image.x=%s&image.y=%s", orgm, cod_localidade, atividade, nome, uf_busca, imagex, imagey);

        driver.get(url);

        List<WebElement> contacts = driver.findElements(By.cssSelector("div#Content_Regs table[width=\"468\"]"));

        Iterator<WebElement> i = contacts.iterator();
        
        while (i.hasNext()) {
            
            WebElement contact = i.next();
            
            WebElement access = contact.findElement(By.cssSelector("td.text_resultado_ib a"));
            
            WebElement addressE = contact.findElement(By.cssSelector("td.text_endereco_ib[width=\"294\"]"));
            
            String name = access.getText();
            
            String link = access.getAttribute("href");
            
            String address = addressE.getText();
            
            System.out.println(name);
            System.out.println(link);
            System.out.println(address);
            System.out.println("---");
            
        }

        // driver.findElement(By.xpath("//img[contains(@src,'http://img.telelistas.net/img/por_rodape_prox.gif')]")).click();
        // driver.findElement(By.xpath("//img[contains(@src,'http://img.telelistas.net/img/por_rodape_prox.gif')]")).click();
        // driver.findElement(By.xpath("//div[@id='Content_Regs']/table[3]/tbody/tr[2]/td[2]/div/table/tbody/tr[2]/td[2]/span/a/u/b")).click();
        // driver.findElement(By.id("errorTryAgain")).click();
        // System.out.println(driver.getTitle());
    }

}
