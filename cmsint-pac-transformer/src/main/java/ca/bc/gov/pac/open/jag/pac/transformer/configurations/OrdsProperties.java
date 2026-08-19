package ca.bc.gov.pac.open.jag.pac.transformer.configurations;

import ca.bc.gov.open.pac.models.OrdsPropertiesInterface;
import java.util.Objects;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "ords")
public class OrdsProperties implements OrdsPropertiesInterface {
    private String username;
    private String password;
    private String cmsIntOrdsUrl = "http://test.com/cmsint/";
    private String cmsOrdsUrl = "http://test.com/cms/";
    private String entriesEndpoint;
    private String modulePath;

    public String getCmsIntBaseUrl() {
        return cmsIntOrdsUrl + modulePath;
    }

    public String getCmsBaseUrl() {
        return cmsOrdsUrl + modulePath;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCmsIntOrdsUrl() {
        return cmsIntOrdsUrl;
    }

    public void setCmsIntOrdsUrl(String cmsIntOrdsUrl) {
        this.cmsIntOrdsUrl = cmsIntOrdsUrl;
    }

    public String getCmsOrdsUrl() {
        return cmsOrdsUrl;
    }

    public void setCmsOrdsUrl(String cmsOrdsUrl) {
        this.cmsOrdsUrl = cmsOrdsUrl;
    }

    public String getEntriesEndpoint() {
        return entriesEndpoint;
    }

    public void setEntriesEndpoint(String entriesEndpoint) {
        this.entriesEndpoint = entriesEndpoint;
    }

    public String getModulePath() {
        return modulePath;
    }

    public void setModulePath(String modulePath) {
        this.modulePath = modulePath;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof OrdsProperties)) return false;
        OrdsProperties other = (OrdsProperties) o;
        return other.canEqual(this)
                && Objects.equals(this.username, other.username)
                && Objects.equals(this.password, other.password)
                && Objects.equals(this.cmsIntOrdsUrl, other.cmsIntOrdsUrl)
                && Objects.equals(this.cmsOrdsUrl, other.cmsOrdsUrl)
                && Objects.equals(this.entriesEndpoint, other.entriesEndpoint)
                && Objects.equals(this.modulePath, other.modulePath);
    }

    protected boolean canEqual(Object other) {
        return other instanceof OrdsProperties;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                username,
                password,
                cmsIntOrdsUrl,
                cmsOrdsUrl,
                entriesEndpoint,
                modulePath);
    }

    @Override
    public String toString() {
        return "OrdsProperties("
                + "username=" + username
                + ", password=" + password
                + ", cmsIntOrdsUrl=" + cmsIntOrdsUrl
                + ", cmsOrdsUrl=" + cmsOrdsUrl
                + ", entriesEndpoint=" + entriesEndpoint
                + ", modulePath=" + modulePath
                + ")";
    }
}
