# External SprintBot plugin interface and logger v1.0.1

Generated library should be copied to folder ./lib of SprinBot server. Remember to restart SprintBot service after update. 

## Usage - Build Yourself

### Build using [Maven](http://maven.apache.org) to your local Maven repository:

        mvn -DskipTests install

### Use in your Maven project:
```xml
        <!-- https://github.com/GitHubSprint/sprint-bot-ext-interface -->         
        <dependency>
            <groupId>pl.sprint</groupId>
            <artifactId>sprint-bot-ext-interface</artifactId>
            <version>1.0.2</version>            
        </dependency>
```

### Usage examle

#### Create e.g Main.class

```java
    import pl.sprint.chatbot.ext.lib.logger.Logger;
    import pl.sprint.chatbot.ext.lib.ChatBotCustomResultProcessor;
    import pl.sprint.chatbot.ext.lib.Conf;
    import pl.sprint.chatbot.ext.lib.Utils;
    import pl.sprint.chatbot.ext.lib.logger.Logger;

    //Add Constructor and impletment Interface and all requied methods.
    public class Main implements ChatBotCustomResultProcessor
    {
        private final String endpoint;
        private final int timeout;
        
        //setLogger required in Constructor
        public Main() 
        {    
            setLogger("sprintbot.ext.lib"); //name of log file
            
            //example getconfigure parameters
            Conf.configure("services.properties"); //name of config file if used [path=$SPRINBOTSERVER/config/plugins/services.properties]
            endpoint = Conf.getValue("endpoint", "http://some_web_service_wsdl");
            timeout = Integer.parseInt(Conf.getValue("timeout", "8000"));
            
        }

        @Override
        public String processCustomResultPocessor(String session, String parameter, String method) 
        {
            log("parameter: " + parameter + " method: " + method, session);
        }
        @Override
        public void setLogger(String logname) {
            Logger.getInstance().setLogger(logname);
        }

        @Override
        public void log(String message, String session) {
            Logger.getInstance().WriteToLog("Main " + session + " : " + message);
        }
    }
```

### pom.xml settings (not required)

#### Properties section
```xml
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
        <!-- path to MAIN class used in Build section as ${mainClass} -->
        <mainClass>pl.sprint.chatbot.ext.lib.pgg.Main</mainClass>
    </properties>
```

#### Build section
```xml
    <build>
        <plugins>
            <plugin>
                <artifactId>maven-jar-plugin</artifactId>
                <version>3.1.0</version>
                <configuration>
                    <archive>
                        <manifest>
                            <addClasspath>true</addClasspath>
                            <classpathPrefix>lib/</classpathPrefix>
                            <mainClass>${mainClass}</mainClass>
                        </manifest>
                    </archive>            
                </configuration>

            </plugin>
            <plugin>
                <artifactId>maven-assembly-plugin</artifactId>
                <configuration>
                    <archive>
                        <manifest> 
                            <mainClass>${mainClass}</mainClass>                 
                        </manifest> 
                    </archive>
                  <descriptorRefs>
                    <descriptorRef>jar-with-dependencies</descriptorRef>
                  </descriptorRefs>
                </configuration>
                <executions>
                  <execution>
                    <id>simple-command</id>
                    <phase>package</phase>
                    <goals>
                      <goal>attached</goal>
                    </goals>
                  </execution>
                </executions>
            </plugin>        
            <plugin>
               <groupId>org.apache.maven.plugins</groupId>
               <artifactId>maven-antrun-plugin</artifactId>
               <version>1.8</version>
               <executions>
                   <execution>
                       <phase>install</phase>
                       <configuration>
                           <target>
                               <copy file="target/${project.artifactId}-${project.version}-jar-with-dependencies.jar" tofile="target/${project.artifactId}.jar"/>                               
                           </target>
                       </configuration>
                       <goals>
                           <goal>run</goal>
                       </goals>
                   </execution>
               </executions>
           </plugin>
        </plugins>
    </build>
```
