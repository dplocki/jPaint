FROM centos

RUN cd /etc/yum.repos.d/
RUN sed -i 's/mirrorlist/#mirrorlist/g' /etc/yum.repos.d/CentOS-*
RUN sed -i 's|#baseurl=http://mirror.centos.org|baseurl=http://vault.centos.org|g' /etc/yum.repos.d/CentOS-*

RUN yum -y install java-1.8.0-openjdk-devel
RUN yum -y install java

WORKDIR /app

COPY src /app/src

RUN export JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF8; javac -classpath libs/*:. -d . -sourcepath . src/core/tools/*.java src/core/*.java src/gui/*.java

CMD java gui/MainJPaintClass
