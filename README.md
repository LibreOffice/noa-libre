# noa-libre
UNO API of LibreOffice easy and understandable - Nice Office Access

NOA-libre is an object-oriented lightweight Java wrapper around LibreOffice UNO API, providing higher-level  abstraction of 
many UNO interfaces. It is a fork of Ubion's NOA (Nice Office Access) built around API of OpenOffice.org.

## Building

To build NOA-libre, [maven-ant-tasks](https://maven.apache.org/ant-tasks/) is needed to pull LibreOffice Java artifacts from 
Maven central repository. Install it into your ant's lib/ directory or adjust ${ant.lib} property in build.xml accordingly to 
point to maven-ant-tasks location.

Additionally, you need swt.jar added to your build path. Download the matching version for your operating system from http://maven-eclipse.github.io/maven and add it to the build path (e.g. place it in `lib` folder.

## Release Notes

Please read [Release Notes](https://github.com/LibreOffice/noa-libre/releases/tag/v3.0.0) if you are upgrading from older versions of NOA-libre
