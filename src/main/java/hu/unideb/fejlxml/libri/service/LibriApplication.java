package hu.unideb.fejlxml.libri.service;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.Server;

import org.restlet.data.Protocol;

import org.restlet.routing.Router;

public class LibriApplication extends Application {

    public static void main(String[] args) {
        Component component = new Component();
        component.getDefaultHost().attach("/libri", new LibriApplication());
        Server server = new Server(Protocol.HTTP, 8111, component);

        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Restlet createInboundRoot() {
        Router router = new Router(getContext());
        router.setDefaultMatchingQuery(true);
        router.attach("/search?{query}", SearchResource.class);
        router.attach("/book/{productCode}", BookResource.class);
        return router;
    }
}
