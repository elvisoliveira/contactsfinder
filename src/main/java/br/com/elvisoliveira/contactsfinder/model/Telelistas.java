package br.com.elvisoliveira.contactsfinder.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Telelistas
{

    private final FirefoxDriver driver;
    private final DesiredCapabilities capability;
    private final ArrayList<String> drivers = new ArrayList<>();

    private final String xpErro = "//td[contains(@background,'http://img.telelistas.net/img/por_fundotopo_erro.gif')]";
    private final String xpNext = "//img[contains(@src,'http://img.telelistas.net/img/por_rodape_prox.gif')]/parent::a";

    public Telelistas()
    {
        capability = DesiredCapabilities.firefox();
        capability.setCapability("platform", Platform.ANY);
        capability.setCapability("binary", "/bin/firefox");

        driver = new FirefoxDriver(capability);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    public void close()
    {
        driver.close();
        driver.quit();
    }

    public void getPage(String url)
    {
        // @todo: set timeout in the get method, if take too long, try again.
        // on the third failed attempt, stop the program and send an email warning.
        driver.get(url);

        boolean found = driver.findElements(By.xpath(xpErro)).isEmpty();

        // detect if there was an empty results page
        if (found)
        {
            // save the current driver in the global variable "drivers"
            drivers.add(driver.getPageSource());

            // detect the presence of next button
            while (getNext(driver))
            {
                // if yes, click on it
                driver.findElement(By.xpath(xpNext)).click();

                // save the current loaded "driver" in the "drivers" global variable
                drivers.add(driver.getPageSource());
            }
        }
        else
        {
            // no results
            System.out.println("no result");
        }
    }

    private boolean getNext(FirefoxDriver driver)
    {
        boolean next = driver.findElements(By.xpath(xpNext)).isEmpty();
        return !(next);
    }

    public ArrayList<HashMap> getContacts()
    {
        ArrayList<HashMap> contactsList = new ArrayList<>();

        // loop the "drivers" global variable
        for (String html : drivers)
        {
            // in each case, loop check if there is more than one page, if yes retrive the 
            // current page contacts and click on next button
            // put the retrive contacts algoritm in another method
            // change to jsoup

            Document doc = Jsoup.parse(html);

            Elements contacts = doc.select("div#Content_Regs > table");

            for (Element contact : contacts)
            {
                String name = contact.select("td.text_resultado_ib > a").text();
                String link = contact.select("td.text_resultado_ib > a").attr("href");
                String addr = contact.select("td.text_endereco_ib").text();

                HashMap<String, String> info = new HashMap<>();

                // @TODO: check if the contact exist in the database if not, register it
                info.put("name", name);
                info.put("link", link);
                info.put("addr", addr);

                contactsList.add(info);
            }
        }

        return contactsList;
    }

}
