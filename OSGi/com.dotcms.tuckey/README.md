
# README

This bundle plugin is an example of how add Tuckey rewrite Rules using an OSGI bundle plugin.

## How to build this example

To install all you need to do is build the JAR. to do this run
`./gradlew jar`
This will build a jar in the build/libs directory

* To install this bundle:
    Copy the bundle jar file inside the Felix OSGI container (dotCMS/felix/load).
            OR
    Upload the bundle jar file using the dotCMS UI (CMS Admin->Dynamic Plugins->Upload Plugin).

* To uninstall this bundle:
    Remove the bundle jar file from the Felix OSGI container (dotCMS/felix/load).
            OR
    Undeploy the bundle using the dotCMS UI (CMS Admin->Dynamic Plugins->Undeploy).

## How to create a bundle plugin for Tuckeys rewrite Rules

In order to create this OSGI plugin, you must create a META-INF/MANIFEST to be inserted into OSGI jar.
This file is being created for you by Gradle. If you need you can alter our config for this but in general our out of the box config should work.
The Gradle plugin uses BND to generate the Manifest. The main reason you need to alter the config is when you need to exclude a package you are including on your Bundle-ClassPath

If you are building the MANIFEST on your own or desire more info on it below is a description of what is required in this MANIFEST you must specify (see template plugin):

* *Bundle-Name*: The name of your bundle
* *Bundle-SymbolicName*: A short an unique name for the bundle
* *Bundle-Activator*: Package and name of your Activator class (example: com.dotmarketing.osgi.tuckey.Activator)
* *DynamicImport-Package: **
Dynamically add required imports the plugin may need without add them explicitly
* *Import-Package*: This is a comma separated list of package's name. In this list there must be the packages that you are
using inside the bundle plugin and that are exported by the dotCMS runtime.

## Activator

This bundle activator extends from *com.dotmarketing.osgi.GenericBundleActivator* and implements *BundleActivator.start()*.
Will manually register Tuckey Rewrite Rules making use of the method *addRewriteRule*

* PLEASE note the *unregisterServices()* call on the *stop* method, this call is MANDATORY (!) as it will allow us to stop and remove the registered Tuckey Rewrite Rules when the plugin is undeploy.

________________________________________________________________________________________

## Testing

* *Testing the forward rule (non dotCMS resource)*: http://localhost:8080/example/forwardTuckey/
* *Testing the forward rule (dotCMS resource)*: http://localhost:8080/example/forwardDotCMS/
* *Testing the redirect rule*: http://localhost:8080/example/redirect/
* *Testing the rewrite rule*: http://localhost:8080/example/rewrite/locations/
* *Testing the conditions*: http://localhost:8080/example/condition/ -> If call it using Google Chrome it will redirect to the about-us page and add an URL Parameter named "browser" with the value "chrome" (about-us/?browser=chrome), if is called from another browser will show up the 404 error page.



