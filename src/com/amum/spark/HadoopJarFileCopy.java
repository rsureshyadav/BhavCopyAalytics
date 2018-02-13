package com.amum.spark;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class HadoopJarFileCopy {

	public static void main(String[] args) throws IOException {
		List<String> jarList = getJarFile();
		String path="E:\\amum\\amumtrade\\BhavCopyAalytics\\input\\dailyreports\\output\\hadoop";
		for(String fileName : jarList){
			String target = fileName.replace(".m2", "hadoop");
			//System.out.println(target);
			File source = new File(fileName);
			File dest = new File(target);
			target = target.substring(0, target.lastIndexOf("\\"));
		
			long start = System.nanoTime();
			createDirIfNotExist(target);
			copyFileUsingJava7Files(source, dest);
			long end = System.nanoTime();
			System.out.println("Time taken by FileChannels Copy = " + (end - start));
		}
	}

	private static void createDirIfNotExist(String directoryName) throws IOException {
		System.out.println(">>dir name>>"+directoryName);
		
		Path path = Paths.get(directoryName);
		Files.createDirectories(path);

	}

	private static void copyFileUsingJava7Files(File source, File dest) throws IOException {
		Files.copy(source.toPath(), dest.toPath());
		
	}

	private static List<String> getJarFile() {
		List<String> fileList = new ArrayList<String>(); 
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\apache\\spark\\spark-core_2.10\\1.3.1\\spark-core_2.10-1.3.1.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\com\\twitter\\chill_2.10\\0.5.0\\chill_2.10-0.5.0.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\com\\esotericsoftware\\kryo\\kryo\\2.21\\kryo-2.21.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\com\\esotericsoftware\\reflectasm\\reflectasm\\1.07\\reflectasm-1.07-shaded.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\com\\esotericsoftware\\minlog\\minlog\\1.2\\minlog-1.2.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\objenesis\\objenesis\\1.2\\objenesis-1.2.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\com\\twitter\\chill-java\\0.5.0\\chill-java-0.5.0.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\apache\\hadoop\\hadoop-client\\2.2.0\\hadoop-client-2.2.0.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\apache\\hadoop\\hadoop-common\\2.2.0\\hadoop-common-2.2.0.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\commons-cli\\commons-cli\\1.2\\commons-cli-1.2.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\apache\\commons\\commons-math\\2.1\\commons-math-2.1.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\xmlenc\\xmlenc\\0.52\\xmlenc-0.52.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\commons-io\\commons-io\\2.1\\commons-io-2.1.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\commons-logging\\commons-logging\\1.1.1\\commons-logging-1.1.1.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\commons-lang\\commons-lang\\2.5\\commons-lang-2.5.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\commons-configuration\\commons-configuration\\1.6\\commons-configuration-1.6.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\commons-collections\\commons-collections\\3.2.1\\commons-collections-3.2.1.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\commons-digester\\commons-digester\\1.8\\commons-digester-1.8.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\commons-beanutils\\commons-beanutils\\1.7.0\\commons-beanutils-1.7.0.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\commons-beanutils\\commons-beanutils-core\\1.8.0\\commons-beanutils-core-1.8.0.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\codehaus\\jackson\\jackson-core-asl\\1.8.8\\jackson-core-asl-1.8.8.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\codehaus\\jackson\\jackson-mapper-asl\\1.8.8\\jackson-mapper-asl-1.8.8.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\apache\\avro\\avro\\1.7.4\\avro-1.7.4.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\com\\google\\protobuf\\protobuf-java\\2.5.0\\protobuf-java-2.5.0.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\apache\\hadoop\\hadoop-auth\\2.2.0\\hadoop-auth-2.2.0.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\apache\\commons\\commons-compress\\1.4.1\\commons-compress-1.4.1.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\tukaani\\xz\\1.0\\xz-1.0.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\apache\\hadoop\\hadoop-hdfs\\2.2.0\\hadoop-hdfs-2.2.0.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\mortbay\\jetty\\jetty-util\\6.1.26\\jetty-util-6.1.26.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\apache\\hadoop\\hadoop-mapreduce-client-app\\2.2.0\\hadoop-mapreduce-client-app-2.2.0.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\apache\\hadoop\\hadoop-mapreduce-client-common\\2.2.0\\hadoop-mapreduce-client-common-2.2.0.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\apache\\hadoop\\hadoop-yarn-client\\2.2.0\\hadoop-yarn-client-2.2.0.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\com\\google\\inject\\guice\\3.0\\guice-3.0.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\javax\\inject\\javax.inject\\1\\javax.inject-1.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\aopalliance\\aopalliance\\1.0\\aopalliance-1.0.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\com\\sun\\jersey\\jersey-test-framework\\jersey-test-framework-grizzly2\\1.9\\jersey-test-framework-grizzly2-1.9.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\com\\sun\\jersey\\jersey-test-framework\\jersey-test-framework-core\\1.9\\jersey-test-framework-core-1.9.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\javax\\servlet\\javax.servlet-api\\3.0.1\\javax.servlet-api-3.0.1.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\com\\sun\\jersey\\jersey-client\\1.9\\jersey-client-1.9.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\com\\sun\\jersey\\jersey-grizzly2\\1.9\\jersey-grizzly2-1.9.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\glassfish\\grizzly\\grizzly-http\\2.1.2\\grizzly-http-2.1.2.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\glassfish\\grizzly\\grizzly-framework\\2.1.2\\grizzly-framework-2.1.2.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\glassfish\\gmbal\\gmbal-api-only\\3.0.0-b023\\gmbal-api-only-3.0.0-b023.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\glassfish\\external\\management-api\\3.0.0-b012\\management-api-3.0.0-b012.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\glassfish\\grizzly\\grizzly-http-server\\2.1.2\\grizzly-http-server-2.1.2.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\glassfish\\grizzly\\grizzly-rcm\\2.1.2\\grizzly-rcm-2.1.2.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\glassfish\\grizzly\\grizzly-http-servlet\\2.1.2\\grizzly-http-servlet-2.1.2.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\glassfish\\javax.servlet\\3.1\\javax.servlet-3.1.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\com\\sun\\jersey\\jersey-server\\1.9\\jersey-server-1.9.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\asm\\asm\\3.1\\asm-3.1.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\com\\sun\\jersey\\jersey-core\\1.9\\jersey-core-1.9.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\com\\sun\\jersey\\jersey-json\\1.9\\jersey-json-1.9.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\codehaus\\jettison\\jettison\\1.1\\jettison-1.1.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\stax\\stax-api\\1.0.1\\stax-api-1.0.1.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\com\\sun\\xml\\bind\\jaxb-impl\\2.2.3-1\\jaxb-impl-2.2.3-1.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\javax\\xml\\bind\\jaxb-api\\2.2.2\\jaxb-api-2.2.2.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\javax\\activation\\activation\\1.1\\activation-1.1.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\codehaus\\jackson\\jackson-jaxrs\\1.8.3\\jackson-jaxrs-1.8.3.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\codehaus\\jackson\\jackson-xc\\1.8.3\\jackson-xc-1.8.3.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\com\\sun\\jersey\\contribs\\jersey-guice\\1.9\\jersey-guice-1.9.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\apache\\hadoop\\hadoop-yarn-server-common\\2.2.0\\hadoop-yarn-server-common-2.2.0.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\apache\\hadoop\\hadoop-mapreduce-client-shuffle\\2.2.0\\hadoop-mapreduce-client-shuffle-2.2.0.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\apache\\hadoop\\hadoop-yarn-api\\2.2.0\\hadoop-yarn-api-2.2.0.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\apache\\hadoop\\hadoop-mapreduce-client-core\\2.2.0\\hadoop-mapreduce-client-core-2.2.0.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\apache\\hadoop\\hadoop-yarn-common\\2.2.0\\hadoop-yarn-common-2.2.0.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\apache\\hadoop\\hadoop-mapreduce-client-jobclient\\2.2.0\\hadoop-mapreduce-client-jobclient-2.2.0.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\apache\\hadoop\\hadoop-annotations\\2.2.0\\hadoop-annotations-2.2.0.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\apache\\spark\\spark-network-common_2.10\\1.3.1\\spark-network-common_2.10-1.3.1.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\apache\\spark\\spark-network-shuffle_2.10\\1.3.1\\spark-network-shuffle_2.10-1.3.1.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\net\\java\\dev\\jets3t\\jets3t\\0.7.1\\jets3t-0.7.1.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\commons-codec\\commons-codec\\1.3\\commons-codec-1.3.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\commons-httpclient\\commons-httpclient\\3.1\\commons-httpclient-3.1.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\apache\\curator\\curator-recipes\\2.4.0\\curator-recipes-2.4.0.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\apache\\curator\\curator-framework\\2.4.0\\curator-framework-2.4.0.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\apache\\curator\\curator-client\\2.4.0\\curator-client-2.4.0.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\apache\\zookeeper\\zookeeper\\3.4.5\\zookeeper-3.4.5.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\jline\\jline\\0.9.94\\jline-0.9.94.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\com\\google\\guava\\guava\\14.0.1\\guava-14.0.1.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\eclipse\\jetty\\orbit\\javax.servlet\\3.0.0.v201112011016\\javax.servlet-3.0.0.v201112011016.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\apache\\commons\\commons-lang3\\3.3.2\\commons-lang3-3.3.2.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\apache\\commons\\commons-math3\\3.1.1\\commons-math3-3.1.1.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\com\\google\\code\\findbugs\\jsr305\\1.3.9\\jsr305-1.3.9.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\slf4j\\slf4j-api\\1.7.10\\slf4j-api-1.7.10.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\slf4j\\jul-to-slf4j\\1.7.10\\jul-to-slf4j-1.7.10.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\slf4j\\jcl-over-slf4j\\1.7.10\\jcl-over-slf4j-1.7.10.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\log4j\\log4j\\1.2.17\\log4j-1.2.17.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\slf4j\\slf4j-log4j12\\1.7.10\\slf4j-log4j12-1.7.10.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\com\\ning\\compress-lzf\\1.0.0\\compress-lzf-1.0.0.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\xerial\\snappy\\snappy-java\\1.1.1.6\\snappy-java-1.1.1.6.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\net\\jpountz\\lz4\\lz4\\1.2.0\\lz4-1.2.0.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\roaringbitmap\\RoaringBitmap\\0.4.5\\RoaringBitmap-0.4.5.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\commons-net\\commons-net\\2.2\\commons-net-2.2.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\spark-project\\akka\\akka-remote_2.10\\2.3.4-spark\\akka-remote_2.10-2.3.4-spark.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\spark-project\\akka\\akka-actor_2.10\\2.3.4-spark\\akka-actor_2.10-2.3.4-spark.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\com\\typesafe\\config\\1.2.1\\config-1.2.1.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\io\\netty\\netty\\3.8.0.Final\\netty-3.8.0.Final.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\spark-project\\protobuf\\protobuf-java\\2.5.0-spark\\protobuf-java-2.5.0-spark.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\uncommons\\maths\\uncommons-maths\\1.2.2a\\uncommons-maths-1.2.2a.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\spark-project\\akka\\akka-slf4j_2.10\\2.3.4-spark\\akka-slf4j_2.10-2.3.4-spark.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\scala-lang\\scala-library\\2.10.4\\scala-library-2.10.4.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\json4s\\json4s-jackson_2.10\\3.2.10\\json4s-jackson_2.10-3.2.10.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\json4s\\json4s-core_2.10\\3.2.10\\json4s-core_2.10-3.2.10.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\json4s\\json4s-ast_2.10\\3.2.10\\json4s-ast_2.10-3.2.10.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\scala-lang\\scalap\\2.10.0\\scalap-2.10.0.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\scala-lang\\scala-compiler\\2.10.0\\scala-compiler-2.10.0.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\apache\\mesos\\mesos\\0.21.0\\mesos-0.21.0-shaded-protobuf.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\io\\netty\\netty-all\\4.0.23.Final\\netty-all-4.0.23.Final.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\com\\clearspring\\analytics\\stream\\2.7.0\\stream-2.7.0.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\io\\dropwizard\\metrics\\metrics-core\\3.1.0\\metrics-core-3.1.0.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\io\\dropwizard\\metrics\\metrics-jvm\\3.1.0\\metrics-jvm-3.1.0.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\io\\dropwizard\\metrics\\metrics-json\\3.1.0\\metrics-json-3.1.0.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\io\\dropwizard\\metrics\\metrics-graphite\\3.1.0\\metrics-graphite-3.1.0.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\com\\fasterxml\\jackson\\core\\jackson-databind\\2.4.4\\jackson-databind-2.4.4.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\com\\fasterxml\\jackson\\core\\jackson-annotations\\2.4.0\\jackson-annotations-2.4.0.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\com\\fasterxml\\jackson\\core\\jackson-core\\2.4.4\\jackson-core-2.4.4.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\com\\fasterxml\\jackson\\module\\jackson-module-scala_2.10\\2.4.4\\jackson-module-scala_2.10-2.4.4.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\scala-lang\\scala-reflect\\2.10.4\\scala-reflect-2.10.4.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\com\\thoughtworks\\paranamer\\paranamer\\2.6\\paranamer-2.6.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\apache\\ivy\\ivy\\2.4.0\\ivy-2.4.0.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\oro\\oro\\2.0.8\\oro-2.0.8.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\tachyonproject\\tachyon-client\\0.5.0\\tachyon-client-0.5.0.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\tachyonproject\\tachyon\\0.5.0\\tachyon-0.5.0.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\spark-project\\pyrolite\\2.0.1\\pyrolite-2.0.1.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\net\\sf\\py4j\\py4j\\0.8.2.1\\py4j-0.8.2.1.jar");
		fileList.add("C:\\Users\\SweetHome\\.m2\\repository\\org\\spark-project\\spark\\unused\\1.0.0\\unused-1.0.0.jar");
		return fileList;
	}

}
