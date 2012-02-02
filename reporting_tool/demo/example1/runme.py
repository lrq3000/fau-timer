import fau_timer

# The request we send to the server
host = "localhost"
request_a = "GET /example1/index.php?q=Apache HTTP/1.1\r\nHost: " + host + "\r\n\r\n"
request_b = "GET /example1/index.php?q=ADDENDUM HTTP/1.1\r\nHost: " + host + "\r\n\r\n"

# Warmup to fill caches etc...
print "Warmup: send 10 requests\r"
for i in range(10):
        fau_timer.init()
        fau_timer.send_request(host, 80, request_a)

times = 100 # number of request send to the server

# Open file for output
f = open('output.csv', 'w')

# Do the request "times" times
for i in range(times):
        # initialize fau_timer
        fau_timer.init()

        # Send the request and measure the response time
        if i%2 == 0:
                fau_timer.send_request(host, 80, request_a)
        else:
                fau_timer.send_request(host, 80, request_b)

        # Now get the ticks and the time from fau_timer
        fau_timer.calculate_time()

        # Get the current cpu speed
        cpuSpeed = fau_timer.get_speed()
        print "Request %i: CPU Speed: %s Hz" % (i, cpuSpeed)

        # Get the time the request has taken in cpu ticks
        cpuTicks = fau_timer.get_cpu_ticks()
        print "Request %i: CPU Ticks: %s" % (i, cpuTicks)

        # Get the time the request has taken in nanoseconds
        time = fau_timer.get_time()
        print "Request %i: Time: %s nanosec" % (i, time)

        # Get the response from the server
        response = fau_timer.get_response()
        #print "Request %i: Response: %s" % (i, response)

        # Write to output file (number_of_request, secret, number_of_ticks)
        print "Writing time to output.csv"
        if i%2 == 0:
                f.write("%i;Apache;%s\n" % (i, time)) 
        else:
                f.write("%i;ADDENDUM;%s\n" % (i, time))
# Close output file       
f.close()
