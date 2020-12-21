docker build -t alekseinz/sumservice . 
docker run -p 8080:8080 --name alekseinz-sumservice -d alekseinz/sumservice