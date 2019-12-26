import sys
from java.util.logging import Logger
from slims.slims import Slims
from slims.criteria import equals
'''
https://buildmedia.readthedocs.org/media/pdf/slims-python-api/latest/slims-python-api.pdf

to communicate using https: in dev environment:

>>> from requests.utils import DEFAULT_CA_BUNDLE_PATH
>>> print(DEFAULT_CA_BUNDLE_PATH)
use location of certifi package cacert file to add selfsigned certs for server endpoint as neccessary.
This will require downloading server's cert and added pem formart cert to end of certifi pem file.
'''

def main():
   
   print("main: hello from python\n", file=sys.stdout, flush=True)
   
   slims = Slims("bigtest", "https://cfvcugimsdev002.fda.gov/slimsrest/", "sherwin.price", "87654321")
   content_records = slims.fetch("Content", equals("cntn_id", "ISO000105913"))
   for content_record in content_records:
        print(content_record.cntn_barCode.value + " " + content_record.cntn_fk_location.displayValue)
   
   print("main: bye from python\n", file=sys.stdout, flush=True)     
main()