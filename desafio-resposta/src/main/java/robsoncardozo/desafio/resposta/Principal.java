/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robsoncardozo.desafio.resposta;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;


/**
 *
 * @author robsoncardozo
 */
public class Principal {

    static CloseableHttpAsyncClient createSSLClient() {
		TrustStrategy acceptingTrustStrategy = new TrustStrategy() {
 
			@Override
			public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
				return true;
			}
		};
 
		SSLContext sslContext = null;
		try {
			sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
		} catch (Exception e) {
			                 System.out.println("Could not create SSLContext");
		}
 
		return HttpAsyncClients.custom()
			.setHostnameVerifier(SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
			.setSSLContext(sslContext).build();
	}

    /**
     * @param args the command line arguments
     * @throws com.mashape.unirest.http.exceptions.UnirestException
     */
    public static void main(String[] args) throws UnirestException {
        Unirest.setAsyncHttpClient(createSSLClient());
        HttpResponse<String> v = Unirest.post("https://api-prova.lab.ca.inf.br:9445/desafio")
           .queryString("name", "Mark")
           .field("last", "Polo")
           .asString();
           v.getBody();
         System.out.println(v.getBody()); 
        
            
        //HttpResponse<JsonNode> jsonResponse = Unirest.post("http://httpbin.org/post")
        //.header("accept", "application/json")
        //.queryString("apiKey", "123")
        //.field("parameter", "value")
        //.field("foo", "bar")
        //.asJson();
        
       
        
        
    }
    
}
