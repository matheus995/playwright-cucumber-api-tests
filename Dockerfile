FROM ubuntu:22.04

USER root

# Set the working directory
WORKDIR /playwright-cucumber-api-test

# Copy project files to the working directory
COPY . /playwright-cucumber-api-test/

# Install necessary packages
RUN apt-get update -y && \
apt-get upgrade -y && \
apt-get install -y wget java-common maven tzdata && \
rm -rf /var/lib/apt/lists/*

# Download and install Amazon Corretto JDK 20
RUN wget -q https://corretto.aws/downloads/latest/amazon-corretto-20-x64-linux-jdk.deb && \
dpkg --install amazon-corretto-20-x64-linux-jdk.deb && \
rm amazon-corretto-20-x64-linux-jdk.deb

# Set the TZ environment variable
ENV TZ=America/Sao_Paulo

# Download and install Allure
RUN wget -q https://github.com/allure-framework/allure2/releases/download/2.23.0/allure-2.23.0.tgz && \
tar -zxvf allure-2.23.0.tgz -C /opt/ && \
ln -s /opt/allure-2.23.0/bin/allure /usr/bin/allure && \
rm allure-2.23.0.tgz

# Change permissions for the working directory
RUN chmod 777 -R /playwright-cucumber-api-test

# Execute Maven build
RUN mvn clean install -DskipTests=true