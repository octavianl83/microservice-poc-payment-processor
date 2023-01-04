# Process Config Management

This module manages the process configuration flows for every tenant; the configuration flow being stored in MongoDB
The module exposes the following REST endpoints:

* **Save tenant configuration** - saves a new process flow configuration for a specific tenant. In case there is already
  a
  configuration for that tenant a new version will be saved.
    * URL: http://localhost:9100/processConfig - POST
* **Get tenant configuration** - gets the latest process flow configuration for a specific tenant.
    * URL: http://localhost:9100/processConfig - GET
* **Upload tenant configuration** - saves a new process flow configuration for a specific tenant by uploading a full
  file.
    * URL: http://localhost:9100/uploadConfig - POST
* **Download tenant configuration** - downloads the latest process flow configuration for a specific tenant in a file
    * URL: http://localhost:9100/uploadConfig - GET

In this [file](ProcessConfigMgmt.postman_collection.json) there are Postman requests that can be used to test this
module