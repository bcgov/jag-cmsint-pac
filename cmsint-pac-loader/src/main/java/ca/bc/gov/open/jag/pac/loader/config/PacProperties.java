package ca.bc.gov.open.jag.pac.loader.config;

import ca.bc.gov.open.pac.models.LoaderPacPropertiesInterface;
import java.util.Objects;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "pac")
public class PacProperties implements LoaderPacPropertiesInterface {

    private String serviceUrl;
    private String pacQueue;
    private String pacRoutingKey;
    private String exchangeName;

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public String getPacQueue() {
        return pacQueue;
    }

    public void setPacQueue(String pacQueue) {
        this.pacQueue = pacQueue;
    }

    public String getPacRoutingKey() {
        return pacRoutingKey;
    }

    public void setPacRoutingKey(String pacRoutingKey) {
        this.pacRoutingKey = pacRoutingKey;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof PacProperties)) return false;
        PacProperties other = (PacProperties) o;
        return other.canEqual(this)
                && Objects.equals(this.serviceUrl, other.serviceUrl)
                && Objects.equals(this.pacQueue, other.pacQueue)
                && Objects.equals(this.pacRoutingKey, other.pacRoutingKey)
                && Objects.equals(this.exchangeName, other.exchangeName);
    }

    protected boolean canEqual(Object other) {
        return other instanceof PacProperties;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                serviceUrl,
                pacQueue,
                pacRoutingKey,
                exchangeName);
    }

    @Override
    public String toString() {
        return "PacProperties("
                + "serviceUrl=" + serviceUrl
                + ", pacQueue=" + pacQueue
                + ", pacRoutingKey=" + pacRoutingKey
                + ", exchangeName=" + exchangeName
                + ")";
    }
}
