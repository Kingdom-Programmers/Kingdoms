FROM ubuntu

# Install dependencies
RUN apt-get update
RUN apt-get install -y git \
    openjdk-17-jdk \
    openjdk-17-jre \
    wget

# Build server
WORKDIR /testmcserver-build
RUN wget -O BuildTools.jar https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar
RUN git config --global --unset core.autocrlf || :
RUN java -jar BuildTools.jar --rev 1.20.4

# Build plugin
COPY . /testmcserver-build/MedievalFactions
WORKDIR /testmcserver-build/MedievalFactions
RUN /testmcserver-build/MedievalFactions/gradlew build

# Copy resources and make post-create.sh executable
COPY ./.testcontainer /resources
RUN chmod +x /resources/post-create.sh

# Run server
WORKDIR /testmcserver
EXPOSE 25565
ENTRYPOINT /resources/post-create.sh
