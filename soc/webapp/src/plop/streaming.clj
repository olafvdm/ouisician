(ns plop.streaming
  (:import 	[java.io IOException]
			[java.io BufferedReader InputStreamReader]
			[java.lang Process]
  			[java.lang ProcessBuilder]))

(defn now [] (.format (java.text.SimpleDateFormat. "dd-MM-yy_HH-mm-ss") (new java.util.Date)))

(def platform-twitch "live.twitch.tv/app/")
(def platform-youtube "a.rtmp.youtube.com/live2/")
(def streamkey-twitch "live_205865829_ICeYXGi2sw5IntQ2uQZavJ9MqWjxDo")	
(def streamkey-youtube "17mp-vm1y-kpzd-2998")			
(def bitrate "96k")
(def string-stream-twitch (str "avconv -f concat -i list.txt -f alsa -i default -map 0:v -map 1:a -c:v copy -c:a aac -ar 44100 -b:a 96k -f flv rtmp://" platform-twitch streamkey-twitch))

(def string-stream-youtube (str "ffmpeg -f concat -i list.txt -f alsa -i hw:CARD=FPGA,DEV=1 -map 0:v -map 1:a -c:v copy -b:a " bitrate " -ar 44100 -c:a aac -f flv rtmp://" platform-youtube streamkey-youtube))

(defn debugProcess [process]
	(def reader (new java.io.BufferedReader (new java.io.InputStreamReader (.getInputStream process))))
	
	(while (not (nil? (.readLine reader)))
		(println (.readLine reader))))


(defn startstream [platform]

	(if (== 1 platform)
		(do
			(println "Starting twitch stream")
			(def string-stream string-stream-twitch)
			(def platform-str "Twitch.tv"))
		)
	(if (== 2 platform)
		(do
			(println "Starting youtube stream")
			(def string-stream string-stream-youtube)
			(def platform-str "Youtube"))
			)
	(println string-stream)
	(def pb-stream (ProcessBuilder. (list "/bin/bash" "-c" "echo HELLO > myfile.txt")))
	(.redirectErrorStream pb-stream true)
	(def process-stream (.start pb-stream))
	
	;(debugProcess process-stream)
	
	(str "Currently live on " platform-str)
	)
	
(defn stopstream []
	(if (boolean (bound? #'process-stream))
	(do 
		(.destroy process-stream)
		(println "Stopping stream")
		(str "Stopping live-stream"))
	(println "No stream started"))	
	)

(defn startrecording []
	(def output-dest (str "resources/public/"))
	(def output-file (str "recordings/oui_" (now) ".wav"))
	(def string-record (str "arecord -c 2 -f S16_LE -r 44100 -t wav -D hw:CARD=FPGA,DEV=1 " output-dest output-file))
	(def pb-record (ProcessBuilder. (list "/bin/bash" "-c" string-record)))
	(def process-record (.start pb-record))
	;(debugProcess process-record)
	(println "Starting recording")
	(str output-file)
	)

(defn stoprecording []
	(.destroy process-record)
	(println "Stopping recording")
	(str output-file)
	)

