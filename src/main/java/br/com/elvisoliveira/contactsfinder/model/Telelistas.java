package br.com.elvisoliveira.contactsfinder.model;

import br.com.elvisoliveira.contactsfinder.beans.ContactBean;
import br.com.elvisoliveira.contactsfinder.dao.ContactsModel;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import net.lightbody.bmp.proxy.ProxyServer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Telelistas
{

    private final FirefoxDriver driver;
    private final ProxyServer server;
    private final DesiredCapabilities capability;
    private final ArrayList<String> drivers = new ArrayList<>();
    private final ArrayList<HashMap> contactsList;

    private final String xpErro = "//td[contains(@background,'http://img.telelistas.net/img/por_fundotopo_erro.gif')]";
    private final String xpNext = "//img[contains(@src,'http://img.telelistas.net/img/por_rodape_prox.gif')]/parent::a";

    private String city;
    private String province;

    public Telelistas() throws Exception
    {
        this.contactsList = new ArrayList<>();

        // generates a random port number
        Integer port;
        try (ServerSocket socket = new ServerSocket(0))
        {
            port = socket.getLocalPort();
            socket.close();
        }

        // start the server and get the selenium proxy object
        server = new ProxyServer(port);
        server.start();
        server.setCaptureHeaders(true);

        // blacklist addresses for faster page load
        server.blacklistRequests("http(s)?://.*\\.scorecardresearch\\.com/.*", 404);
        server.blacklistRequests("http(s)?://.*\\.googletagservices\\.com/.*", 404);
        server.blacklistRequests("http(s)?://.*\\.google-analytics\\.com/.*", 404);
        server.blacklistRequests("http(s)?://.*\\.googleadservices\\.com/.*", 404);
        server.blacklistRequests("http(s)?://.*\\.googlesyndication\\.com/.*", 404);
        server.blacklistRequests("http(s)?://.*\\.facebook\\.com/.*", 404);
        server.blacklistRequests("http(s)?://.*\\.buscape\\.com/.*", 404);
        server.blacklistRequests("http(s)?://.*\\.navdmp\\.com/.*", 404);
        server.blacklistRequests("http(s)?://.*\\.google\\.com/.*", 404);
        server.blacklistRequests("http(s)?://.*\\.buscape\\.com\\.br/.*", 404);
        server.blacklistRequests("http(s)?://.*\\.mundi\\.com\\.br/.*", 404);
        server.blacklistRequests("http(s)?://.*\\.akamaihd\\.net/.*", 404);
        server.blacklistRequests("http(s)?://.*\\.ophertas\\.net/.*", 404);

        // setup proxy server
        Proxy proxy = server.seleniumProxy();

        // setup desired browser capabilities
        capability = DesiredCapabilities.firefox();
        capability.setCapability("platform", Platform.ANY);
        capability.setCapability("binary", "/bin/firefox");
        capability.setCapability(CapabilityType.PROXY, proxy);

        // startup browser
        driver = new FirefoxDriver(capability);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    public void close()
    {
        driver.close();
        driver.quit();
    }

    public void getPage(String name, String city, String province) throws UnsupportedEncodingException
    {
        this.city = city;
        this.province = province;

        String orgm = "0";
        String cod_localidade = city;
        String atividade = "";
        String nome = URLEncoder.encode(name, "ISO-8859-1");
        String uf_busca = province;
        String imagex = "0";
        String imagey = "0";

        String url = String.format("http://www.telelistas.net/templates/resultado_busca.aspx?q=&orgm=%s&cod_localidade=%s&atividade=%s&nome=%s&uf_busca=%s&image.x=%s&image.y=%s", orgm, cod_localidade, atividade, nome, uf_busca, imagex, imagey);

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
        // in each case, loop check if there is more than one page, if yes retrive the 
        // current page contacts and click on next button
        boolean next = driver.findElements(By.xpath(xpNext)).isEmpty();
        return !(next);
    }

    public void getContacts()
    {
        // loop the "drivers" global variable
        for (String html : drivers)
        {
            Document doc = Jsoup.parse(html);

            Elements contacts = doc.select("div#Content_Regs > table");

            for (Element contact : contacts)
            {
                String name = contact.select("td.text_resultado_ib > a").text().trim();
                String link = contact.select("td.text_resultado_ib > a").attr("href").trim();
                String addr = contact.select("td.text_endereco_ib").text().trim();

                HashMap<String, String> info = new HashMap<>();

                // save hashmap of info to display
                info.put("name", name);
                info.put("link", link);
                info.put("addr", addr);

                contactsList.add(info);

                // make the contact a bean
                ContactBean contacOb = new ContactBean();
                
                contacOb.setName(name);
                contacOb.setAddress(addr);
                contacOb.setLink(link);

                contacOb.setCity(this.city);
                contacOb.setProvince(this.province);

                ContactsModel contactM = new ContactsModel();
                contactM.setContact(contacOb);
            }
        }
    }

    // @TODO: method setContacts, will save the contacts in the SQLite database
    // if it was not saved before, so make a validation to detect repeted itens
    // @TODO: method getExternal, make a request to contactsmanager to find if
    // contacts is already in the main contacts database
}
