import sys
from java.util.logging import Logger
from java.util.logging import LogManager
from java.util.logging import Level
'''
genhom slims interface:
https://buildmedia.readthedocs.org/media/pdf/slims-python-api/latest/slims-python-api.pdf

to communicate using https: in dev environment:

>>> from requests.utils import DEFAULT_CA_BUNDLE_PATH
>>> print(DEFAULT_CA_BUNDLE_PATH)
use location of certifi package cacert file to add selfsigned certs for server endpoint as neccessary.
This will require downloading server's cert and added pem formart cert to end of certifi pem file.

to use the python interpreter with the Jep Library: https://github.com/ninia/jep
Java Runtime linkage path may require adjustment depending on the where on the system Jep was installed.
The following setting in the launch specification for the Java Runtime will accomplish this:

-Djava.library.path = python jep sitepackages directory

Of course if on windows or mac this will need some adjustment.

'''


def printlog(s):
   print(s, file=sys.stdout, flush=True)  

    
def main():
   printlog("main: hello from python\n")
   
   LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME).setLevel(Level.FINE)
   logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME)
   logger.info("Test.")
   printlog("main: bye from python\n")     


main()
