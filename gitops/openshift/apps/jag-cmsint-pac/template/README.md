## Templates to create openshift components related to jag-cmsint-pac api deployment

### Command to execute template
1) Login to OC using login command
2) Run below command in each env. namespace dev/test/prod/tools
   ``oc process -f jag-cmsint-pac.yaml --param-file=jag-cmsint-pac.env | oc apply -f -``