package ca.bc.gov.pac.open.jag.pac.transformer.configurations;

import ca.bc.gov.open.pac.models.PacPropertiesInterface;
import java.util.Objects;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "pac")
public class PacProperties implements PacPropertiesInterface {
    private String pacQueue;
    private String pacRoutingKey;
    private String exchangeName;
    private String serviceUrl;
    private String pacDatePattern;
    private String icsDatePattern;
    private String cmsDatePattern;

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

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public String getPacDatePattern() {
        return pacDatePattern;
    }

    public void setPacDatePattern(String pacDatePattern) {
        this.pacDatePattern = pacDatePattern;
    }

    public String getIcsDatePattern() {
        return icsDatePattern;
    }

    public void setIcsDatePattern(String icsDatePattern) {
        this.icsDatePattern = icsDatePattern;
    }

    public String getCmsDatePattern() {
        return cmsDatePattern;
    }

    public void setCmsDatePattern(String cmsDatePattern) {
        this.cmsDatePattern = cmsDatePattern;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof PacProperties)) return false;
        PacProperties other = (PacProperties) o;
        return other.canEqual(this)
                && Objects.equals(this.pacQueue, other.pacQueue)
                && Objects.equals(this.pacRoutingKey, other.pacRoutingKey)
                && Objects.equals(this.exchangeName, other.exchangeName)
                && Objects.equals(this.serviceUrl, other.serviceUrl)
                && Objects.equals(this.pacDatePattern, other.pacDatePattern)
                && Objects.equals(this.icsDatePattern, other.icsDatePattern)
                && Objects.equals(this.cmsDatePattern, other.cmsDatePattern);
    }

    protected boolean canEqual(Object other) {
        return other instanceof PacProperties;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                pacQueue,
                pacRoutingKey,
                exchangeName,
                serviceUrl,
                pacDatePattern,
                icsDatePattern,
                cmsDatePattern);
    }

    @Override
    public String toString() {
        return "PacProperties("
                + "pacQueue=" + pacQueue
                + ", pacRoutingKey=" + pacRoutingKey
                + ", exchangeName=" + exchangeName
                + ", serviceUrl=" + serviceUrl
                + ", pacDatePattern=" + pacDatePattern
                + ", icsDatePattern=" + icsDatePattern
                + ", cmsDatePattern=" + cmsDatePattern
                + ")";
    }
}
