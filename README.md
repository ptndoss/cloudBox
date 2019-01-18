# cloudBox
Cloud Drive to store and retrive files backed by AWS.

Introduction

University Name: http://www.sjsu.edu/
Course: Cloud Technologies
Professor: Sanjay Garje



Cloud Box, is a web application hosted on AWS cloud which intends to provide Authorized users a portal to securely maintain their files on cloud. The project illustrates the use of various AWS cloud components in developing a 3 Tier Web Application. The application manages various components to provide a highly available, scalable, cost effective solution to securely back up data on to Amazon S3. Cloud Box application leverages AWS auto scaling functionality to provide seamless experience during peak load times. The application also monitors the health of the EC2 associated with auto scale group using Cloud Watch, AWS Lambda and SNS. Users can perform the following activities on the portal. 

Integrated CI/CD for continous integration and Deployment using Code Pipeline and Jenkins.

    Upload file to S3
    Download Files from S3 via Cloud Front
    Delete Files on S3
    Edit the already uploaded files. All the above operations are securely handled using AWS VPC and S3 Transfer Acceleration. IAM users with appropriate roles are in place to ensure a secure cloud experience.
    Admin has access to delete files of any users.

    
AWS Components Leveraged

    EC2: Create an EC2 instance with all project artifacts and use that to obtain an AMI for the AutoScale Group deployment.
    AutoScaling Group: To achieve high-available and scalable solution configure auto scale group with a desired instance of 1 and max instance of 2. This is configurable based on requirement and traffic the website attracts.
    Classic Load Balancer: Load Balancer point to autoscale group so that it handles optimal load on all the EC2 instances associated with the group.
    S3: Used to upload and maintain user files.
    S3 Transfer Acceleration: S3 bucket is enabled with Transfer Acceleration to enable faster and secure transfer of files to S3.
    Standard Infrequent Access (IA): Lifecycle policies are updated on S3 to move files to IA after 75 days.
    Amazon Glacier: Lifecycle policies are enabled on S3 bucket to move files to Amazon Glacier after 365 days.
    CloudFront: File download is done using CloudFront. The minimum TTL for CloudFront is setup as 1minute.
    RDS: MySQL instance is created to maintain user data and file metadata.
    CloudWatch: Cloud watch is used to monitor any additional spin up or termination of EC2 in the autoscale group. It triggers Lambda when such a event occurs. Cloudwatch alarms are configured to send notifications via SNS on S3 and ELB healthchecks.
    Lambda: A python program is configured to invoke SNS topic to send notifications.
    SNS: Configured to send email to all the subscribers for the topic.
    Route53: Domain Name Server that resolves IP address for the application domain www.thirumalainambidoss.me


