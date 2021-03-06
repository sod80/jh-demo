#!/bin/bash
# Files are ordered in proper order with needed wait for the dependent custom resource definitions to get initialized.
# Usage: bash kubectl-apply.sh

kubectl apply -f registry/
kubectl apply -f blog/
kubectl apply -f greeting/
kubectl apply -f store/
kubectl apply -f messagebroker/
kubectl apply -f console/
