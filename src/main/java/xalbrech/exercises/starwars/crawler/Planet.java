package xalbrech.exercises.starwars.crawler;

import java.net.URL;
import java.util.Collection;

public class Planet {

    private String name;
    private URL url;
    private Collection<URL> residents;
    private Collection<URL> films;

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public void setResidents(Collection<URL> residents) {
        this.residents = residents;
    }

    public void setFilms(Collection<URL> films) {
        this.films = films;
    }

    public String getName() {
        return name;
    }

    public Collection<URL> getResidents() {
        return residents;
    }

    public Collection<URL> getFilms() {
        return films;
    }

    public URL getUrl() {
        return url;
    }
}
