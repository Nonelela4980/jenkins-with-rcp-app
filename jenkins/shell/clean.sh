echo "===========================STARTED MAVEN========================"
echo "cleaning......."

ls

cd ../../parent

mvn clean install package

echo "===========================MAVEN CLEAN COMPLETED========================"


